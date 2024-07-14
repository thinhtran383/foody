package com.example.foodordering.configuration;

import com.example.foodordering.components.CustomAccessDeniedHandler;
import com.example.foodordering.components.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Value("${api.v1.prefix}")
    String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> {
                    request.requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/",
                                    "/v3/api-docs/**",
                                    "/api-docs",
                                    "/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",

                                    // user
                                    String.format("%s/roles", apiPrefix),
                                    String.format("%s/users/**", apiPrefix),
                                    String.format("%s/users/", apiPrefix),

                                    // menu
                                    String.format("%s/menu/**", apiPrefix),
                                    String.format("%s/menu", apiPrefix),
                                    "/new",

                                    // category
                                    String.format("%s/categories", apiPrefix),
                                    String.format("%s/categories/**", apiPrefix),
                                    "/error/**",

                                    // table
                                    String.format("%s/tables/**",apiPrefix)



                            ).permitAll()
                            .anyRequest()
                            .authenticated();


                })
                .authenticationProvider(authenticationProvider);


        return http.build();
    }
}
