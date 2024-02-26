package ar.edu.itba.paw.webapp.config;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

public class UnconditionalCacheFilter extends OncePerRequestFilter {

    public static final long MAX_TIME = Duration.ofDays(365).getSeconds();
//    https://developer.mozilla.org/es/docs/Web/HTTP/Headers/Cache-Control
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Cache-Control",String.format("public, max-age=%d, inmutable",MAX_TIME));
        filterChain.doFilter(request,response);
    }
}
