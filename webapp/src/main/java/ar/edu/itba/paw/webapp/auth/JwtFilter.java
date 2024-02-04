package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {


    final JwtUtils jwtUtils;

    final UserService userService;

    final AuthenticationEntryPoint authenticationEntryPoint;

    final AuthenticationManager authenticationManager;

    public JwtFilter(final JwtUtils jwtUtils, final UserService userService, final AuthenticationEntryPoint authenticationEntryPoint, final AuthenticationManager authenticationManager){
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        final String token = header.split(" ")[1].trim();

        if(!jwtUtils.validateToken(token)){
            authenticationEntryPoint.commence(httpServletRequest,httpServletResponse,new BadCredentialsException(""));
            return;
//            SecurityContextHolder.clearContext();
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//            return;
        }
        try {
            final Authentication authentication = authenticationManager.authenticate(new EmailAuthenticationToken(jwtUtils.getEmail(token)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (AuthenticationException e){
            authenticationEntryPoint.commence(httpServletRequest,httpServletResponse,e);
            return;
//            SecurityContextHolder.clearContext();
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//            return;
        }

        //User is already authenticated, in the case of a refresh token, the new token is sent

        if(jwtUtils.isRefreshToken(token)){
//            The token is a refresh token
            final Optional<User> user = userService.findByEmail(jwtUtils.getEmail(token));
            if(!user.isPresent()){
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            final String baseUrl = httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
            header = "Bearer " +  jwtUtils.createToken(user.get(),baseUrl);
            httpServletResponse.setHeader(BasicAuthFilter.JWT_HEADER,header);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
