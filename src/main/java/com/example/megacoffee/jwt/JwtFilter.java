package com.example.megacoffee.jwt;

import com.example.megacoffee.dto.ApiResponse;
import com.example.megacoffee.model.AdminDetailsService;
import com.example.megacoffee.status.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private AdminDetailsService adminDetailsService;
    private List<String> excludeUrls = Arrays.asList("/api/admin/login", "/api/admin/register","/api/car/all","/swagger-ui/index.html","/swagger-ui.html");


    public JwtFilter(JwtUtil jwtUtil, AdminDetailsService adminDetailsService) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (excludeUrls.stream().anyMatch(url -> url.equals(requestURI))) {
            chain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, "Unauthorized: Bearer token is missing or malformed");
            return;
        }
        String token = authorizationHeader.substring(7);
        if (token == null || token.trim().isEmpty() || "undefined".equals(token)) {
            sendErrorResponse(response, "Unauthorized: Token is null, empty or undefined");
            return;
        }

        if (!jwtUtil.validToken(token)){
            sendErrorResponse(response, "Unauthorized: Token is not valid");
            return;
        }

        String username = jwtUtil.extractToken(token).getSubject();
        var userDetails = adminDetailsService.loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        ApiResponse<String> errorResponse = new ApiResponse<>(ResponseStatus.ERROR, "안됨",  message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}