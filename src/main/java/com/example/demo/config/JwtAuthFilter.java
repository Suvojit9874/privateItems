package com.example.demo.config;

import com.example.demo.model.auth.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtService jwtServiceObj;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug(String.format("Public API access remote Ip %s || URL (%s)%s", request.getRemoteAddr(), request.getMethod(), request.getRequestURI()));
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        String email = "";
        try {
            email = jwtServiceObj.getEmail(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                if (jwtServiceObj.isValidToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.debug(String.format("Authorized API access remote Ip %s || URL (%s) %s || user %s(%s)", request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), ((User) (authToken.getPrincipal())).getUserId() , userDetails.getAuthorities().iterator().next().toString()));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.debug(String.format("Unauthorized API access remote Ip %s || URL (%s) %s || Error %s", request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), e.toString()));
        }
        filterChain.doFilter(request, response);

    }
}
