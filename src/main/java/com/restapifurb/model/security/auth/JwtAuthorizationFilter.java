package com.restapifurb.model.security.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Todos os requests feito para a aplicação,
     * irão primeiro passar pelo filter customizado que criamos
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        String accessToken = jwtUtil.getToken(request);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("token para tentativa de acesso : " + accessToken);
        Claims claims = jwtUtil.getClaims(request);

        if (claims != null & jwtUtil.validarClaims(claims)) {
            String email = claims.getSubject();
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(email, "", new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
