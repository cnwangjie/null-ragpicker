package com.nullteam.ragpicker.middleware;

import com.nullteam.ragpicker.config.JWTConfig;
import com.nullteam.ragpicker.service.serviceImpl.JWTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JWTConfig jwtConfig;

    private final JWTServiceImpl jwtService;

    @Autowired
    public JWTAuthenticationTokenFilter(JWTConfig jwtConfig, JWTServiceImpl jwtService) {
        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtConfig.getJWTHeader());
        String identity =  jwtService.getIdentityFromToken(token);

        if (identity != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Object principal;
            Set authorities = new HashSet();
            switch (identity) {
                case "user":
                    principal = jwtService.getUserFromToken(token);
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    break;
                case "collector":
                    principal = jwtService.getCollectorFromToken(token);
                    authorities.add(new SimpleGrantedAuthority("ROLE_COLLECTOR"));
                    break;
                default:
                    principal = null;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, token, authorities);
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
