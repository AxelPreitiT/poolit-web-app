package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class BasicAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private static final long CREDENTIALS_LENGTH = 2;

    private static final int EMAIL_INDEX = 0;

    private static final int PASSWORD_INDEX = 1;

    private static final String REFRESH_HEADER = "Authorization-refresh";

    @Autowired
    public BasicAuthFilter(final JwtUtils jwtUtils, final UserService userService, final AuthenticationManager authenticationManager){
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Basic ")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        try {
            String[] credentials = decodeHeader(header.split(" ")[1]);
            if(credentials.length!=CREDENTIALS_LENGTH){
                return;
            }
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials[EMAIL_INDEX], credentials[PASSWORD_INDEX])
            );
            userService.findByEmail(credentials[EMAIL_INDEX]).ifPresent(u -> {
                httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.createToken(u));
//                https://www.rfc-editor.org/rfc/rfc9110#name-field-extensibility
                httpServletResponse.setHeader(REFRESH_HEADER,"Bearer " + jwtUtils.createRefreshToken(u));
            });
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
//            TODO: ver que hacer acá
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private String[] decodeHeader(String encodedToken){
        byte[] base64Token = encodedToken.getBytes(StandardCharsets.UTF_8);
        byte[] decodedToken;

        try {
            decodedToken = Base64.getDecoder().decode(base64Token);
        }catch (IllegalArgumentException e){
//            TODO: change for custom credentials exception
            throw new IllegalArgumentException();
        }

        String info = new String(decodedToken, StandardCharsets.UTF_8);

        return info.split(":");
    }
}
