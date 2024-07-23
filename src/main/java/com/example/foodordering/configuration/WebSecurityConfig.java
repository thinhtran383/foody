package com.example.foodordering.configuration;

import com.example.foodordering.components.CustomAccessDeniedHandler;
import com.example.foodordering.filter.CorsFilter;
import com.example.foodordering.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Value("${api.v1.prefix}")
    String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(HttpMethod.GET,
                                    // swagger
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


                                    // health check
                                    "/actuator/health",
                                    "/actuator/health/**",

                                    // for landing page
                                    String.format("%s/web-settings", apiPrefix),

                                    // for menu
                                    String.format("%s/menu/**", apiPrefix),


                                    // for category
                                    String.format("%s/categories/all", apiPrefix),

                                    // notification
                                    String.format("%s/notification", apiPrefix),

                                    //table
                                    String.format("%s/tables/all", apiPrefix),

                                    //order
                                    String.format("%s/orders/getOrderDetails", apiPrefix),
                                    "/home/**"


                            )
                            .permitAll();

                    request.requestMatchers(      // for user
                            String.format("%s/users/login", apiPrefix),

                            String.format("%s/orders", apiPrefix),
                            String.format("%s/payment/**", apiPrefix),
                            String.format("%s/payment", apiPrefix),
                            String.format("%s/notifications/**", apiPrefix),

                            String.format("%s/web-settings/**", apiPrefix),
                            String.format("%s/menu/getUrlImage", apiPrefix),
                            String.format("%s/device-tokens", apiPrefix)

                    ).permitAll();

//                    request.requestMatchers(
//                            String.format("%s/orders/**", apiPrefix)
//                    ).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");

                    request.requestMatchers(
                                    String.format("%s/tables/**", apiPrefix),
                                    String.format("%s/orders/**", apiPrefix),
                                    String.format("%s/categories/**", apiPrefix),
                                    String.format("%s/menu/**", apiPrefix),
                                    String.format("%s/users/**", apiPrefix),
                                    String.format("%s/roles/**", apiPrefix),
                                    String.format("%s/web-settings/**", apiPrefix)


                            ).hasAuthority("ROLE_ADMIN")
                            .anyRequest()
                            .authenticated();


                })
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler));


//        http.authorizeHttpRequests(request -> {
//                    request.requestMatchers(
//                                    "/swagger-ui/**",
//                                    "/v3/api-docs/",
//                                    "/v3/api-docs/**",
//                                    "/api-docs",
//                                    "/api-docs/**",
//                                    "/swagger-resources",
//                                    "/swagger-resources/**",
//                                    "/configuration/ui",
//                                    "/configuration/security",
//                                    "/swagger-ui/**",
//                                    "/swagger-ui.html",
//
//                                    // user
//                                    String.format("%s/roles", apiPrefix),
//                                    String.format("%s/users/**", apiPrefix),
//                                    String.format("%s/users/", apiPrefix),
//
//                                    // menu
//                                    String.format("%s/menu/**", apiPrefix),
//                                    String.format("%s/menu", apiPrefix),
//
//
//                                    // category
//                                    String.format("%s/categories", apiPrefix),
//                                    String.format("%s/categories/**", apiPrefix),
//
//
//                                    // web-setting
//                                    String.format("%s/web-settings", apiPrefix),
//                                    // table
//                                    String.format("%s/tables/**",apiPrefix)
//
////                                    "/test"
//
//
//
//                            ).permitAll()
//                            .anyRequest()
//                            .authenticated();
//
//
//                })
//                .authenticationProvider(authenticationProvider);


        return http.build();
    }
}
