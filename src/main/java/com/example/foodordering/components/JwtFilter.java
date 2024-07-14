package com.example.foodordering.components;

import com.example.foodordering.entities.User;
import com.example.foodordering.utils.JwtGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtGenerator jwtGenerator;
    private final UserDetailsService userDetailsService;

    @Value("${api.v1.prefix}")
    String apiPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        System.out.println(request.getServletPath());
        System.out.println(request.getMethod());

        if (isNonAuthRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtGenerator.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = (User) userDetailsService.loadUserByUsername(username);
                if (jwtGenerator.isValidToken(token, user)) {  // Ensure the token is valid for the user
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                filterChain.doFilter(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isNonAuthRequest(HttpServletRequest request) {
        final List<Pair<String, String>> nonAuthRequests = List.of(


                // Test
                Pair.of("/test", "GET"),

                // Swagger
                Pair.of("/swagger-ui/**", "GET"),
                Pair.of("/v3/api-docs/**", "GET"),
                Pair.of("/v3/api-docs", "GET"),

                // Login
                Pair.of(String.format("%s/users/**", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/**", apiPrefix), "PUT"),
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),

                // Error
                Pair.of("/error/**", "GET"),

                //Menu
                Pair.of(String.format("%s/menu/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/menu", apiPrefix), "GET"),

                // Category
                Pair.of(String.format("%s/categories", apiPrefix),"GET"),
                Pair.of(String.format("%s/categories/**", apiPrefix),"GET"),
                Pair.of(String.format("%s/categories/**", apiPrefix),"POST"),

                // Table
                Pair.of(String.format("%s/tables/**", apiPrefix), "GET")

        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> nonAuthRequest : nonAuthRequests) {
            String path = nonAuthRequest.getFirst();
            String method = nonAuthRequest.getSecond();


            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equals(method)) {
                return true;
            }

        }

        return false;
    }

}
