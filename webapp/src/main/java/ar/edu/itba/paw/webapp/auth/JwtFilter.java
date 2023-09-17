package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {


    final JwtUtils jwtUtils;

    final UserDetailsService userDetailsService;

    final UserService userService;

    public JwtFilter(final JwtUtils jwtUtils, final UserDetailsService userDetailsService, final UserService userService){
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        final String token = header.split(" ")[1].trim();

        if(!jwtUtils.validateToken(token)){
//            TODO: check if public resources use this also (they should not)
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(jwtUtils.getEmail(token));
        }catch (Exception e){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        if(userDetails != null && jwtUtils.isRefreshToken(token)){
//            The token is a refresh token
            final Optional<User> user = userService.findByEmail(userDetails.getUsername());
            if(!user.isPresent()){
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtUtils.createToken(user.get()));
        }

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION,header);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
