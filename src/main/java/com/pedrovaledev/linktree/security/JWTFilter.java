package com.pedrovaledev.linktree.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pedrovaledev.linktree.model.User;
import com.pedrovaledev.linktree.repository.UserRepository;
import com.pedrovaledev.linktree.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getToken(request);
            if (token != null) {
                String subject = jwtService.validateToken(token);
                Optional<User> user = userRepository.findByUsername(subject);

                if(user.isPresent()) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    private String getToken(HttpServletRequest request) {
        String authHeader= request.getHeader("Authorization");

        if (!authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
