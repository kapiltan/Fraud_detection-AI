package com.fraudintel.fraudDetection.config;

import com.fraudintel.fraudDetection.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService){
        this.jwtService=jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException{
        String header = request.getHeader("Authorization");

        if(header==null||!header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            Claims claims = jwtService.validateToken(token).getPayload();
//            var claims = jws.getBody();

            String email = claims.getSubject();

            List<String> roles=claims.get("roles",List.class);

            var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();

            AbstractAuthenticationToken authToken = new AbstractAuthenticationToken(authorities) {
                @Override
                public Object getCredentials() {
                    return token;
                }

                @Override
                public Object getPrincipal() {
                    return email;
                }
            };

            authToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
