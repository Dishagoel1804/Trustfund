package com.trustfund.trustfund.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // Societies — only ADMIN can create/delete
                        .requestMatchers(HttpMethod.POST, "/api/societies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/societies/**").hasRole("ADMIN")

                        // Vendors — ADMIN and TREASURER can manage
                        .requestMatchers(HttpMethod.POST, "/api/vendors/**").hasAnyRole("ADMIN", "TREASURER")
                        .requestMatchers(HttpMethod.PUT, "/api/vendors/**").hasAnyRole("ADMIN", "TREASURER")
                        .requestMatchers(HttpMethod.DELETE, "/api/vendors/**").hasAnyRole("ADMIN", "TREASURER")

                        // Users — only ADMIN can delete other users
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // Expenses — ADMIN, TREASURER, COMMITTEE can create; only ADMIN can delete
                        .requestMatchers(HttpMethod.POST, "/api/expenses/**").hasAnyRole("ADMIN", "TREASURER", "COMMITTEE")
                        .requestMatchers(HttpMethod.DELETE, "/api/expenses/**").hasRole("ADMIN")

                                // Dues — only ADMIN/TREASURER can create, update, delete
                                .requestMatchers(HttpMethod.POST, "/api/dues/**").hasAnyRole("ADMIN", "TREASURER")
                                .requestMatchers(HttpMethod.PUT, "/api/dues/**").hasAnyRole("ADMIN", "TREASURER")
                                .requestMatchers(HttpMethod.DELETE, "/api/dues/**").hasAnyRole("ADMIN", "TREASURER")

                        // Disputes — anyone logged in can raise one (POST); only ADMIN/COMMITTEE can resolve or delete
                        .requestMatchers(HttpMethod.PUT, "/api/disputes/**").hasAnyRole("ADMIN", "COMMITTEE")
                        .requestMatchers(HttpMethod.DELETE, "/api/disputes/**").hasAnyRole("ADMIN", "COMMITTEE")
                        // GET requests on dues just need to be logged in (any role) — falls through to anyRequest().authenticated() below

                        // Audit logs — only ADMIN can view (read-only, no create/update/delete endpoints exist anyway)
                        .requestMatchers("/api/audit-logs/**").hasRole("ADMIN")

                        // Anomaly flags — ADMIN/TREASURER/COMMITTEE can manage; residents should not see or create these
                        .requestMatchers("/api/anomaly-flags/**").hasAnyRole("ADMIN", "TREASURER", "COMMITTEE")

                        // User activation/deactivation — only ADMIN can do this
                        .requestMatchers(HttpMethod.PUT, "/api/users/*/activate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/*/deactivate").hasRole("ADMIN")
                        // Everything else just needs to be logged in
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}