package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.exceptions.InvalidTokenException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static final String JWT_HEADER = "JWT-authorization";

    private static final String JWT_REFRESH_HEADER = "JWT-refresh-authorization";

    private static final String VERIFICATION_HEADER = "Account-verification";

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
            final User user = userService.findByEmail(credentials[EMAIL_INDEX]).orElseThrow(IllegalStateException::new);
            //Si esta habilitado, intentamos autenticarlo
            if(user.isEnabled()){
                final Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(credentials[EMAIL_INDEX], credentials[PASSWORD_INDEX])
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                //No está habilitado, intentamos confirmar el registro con el token
                userService.confirmRegister(credentials[PASSWORD_INDEX]);
            }
            //https://www.rfc-editor.org/rfc/rfc9110#name-field-extensibility
            httpServletResponse.setHeader(JWT_HEADER, "Bearer " + jwtUtils.createToken(user));
            httpServletResponse.setHeader(JWT_REFRESH_HEADER,"Bearer " + jwtUtils.createRefreshToken(user));
        }catch (InvalidTokenException ex){
            String[] credentials = decodeHeader(header.split(" ")[1]);
            userService.sendVerificationEmail(credentials[EMAIL_INDEX]);
            httpServletResponse.setHeader(VERIFICATION_HEADER,"true");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }catch (Exception e){
//            Si no manda las credenciales, alguna operación provoca ArrayIndexOutOfBoundsException y cae aca
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
            throw new IllegalArgumentException();
        }

        String info = new String(decodedToken, StandardCharsets.UTF_8);

        return info.split(":");
    }
}
