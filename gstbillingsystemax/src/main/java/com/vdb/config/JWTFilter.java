package com.vdb.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.IllegalFormatException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String custEmail = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                custEmail = jwtUtil.extractUsername(token);

            } catch (IllegalFormatException e) {
                log.warn("unable to get jwt token", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "unable to get jwt token");
            } catch (ExpiredJwtException e) {
                log.warn("expired jwt token", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "expired jwt token");
            } catch (MalformedJwtException e) {
                log.warn("invalid jwt token", e.getCause());
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "invalid jwt token");
            } catch (SignatureException e) {
                log.warn("invalid jwt signature", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "invalid jwt signature");
            } catch (Exception e) {
                log.warn("Internal Server error", e.getCause());
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server error");
            }
        }

        if (custEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(custEmail);

            if (jwtUtil.isTokenValidate(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } else {
                log.warn("invalid jwt token");
                response.sendError(HttpStatus.BAD_REQUEST.value(), "invalid jwt signature");
            }

        }
        filterChain.doFilter(request, response);

    }
}
