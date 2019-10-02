package org.sk.security.demo.config.security;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author satish sharma
 *
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    private TokenService jwtTokenProvider;

    public JWTFilter(TokenService jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     *  Extract Authorization header and validate token. 
     *  IF token is valid the set the {@link Principal} 
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {
    	
        String token = jwtTokenProvider.getTokenValue(((HttpServletRequest)req).getHeader(HttpHeaders.AUTHORIZATION));
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }
}