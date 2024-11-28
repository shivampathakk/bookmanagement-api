package com.api.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.api.book.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for stateless APIs
            .authorizeHttpRequests(authorize -> authorize
                // Allow anyone to access Swagger UI and OpenAPI docs
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()

                // Allow both ADMIN and USER to access /books endpoints
                .requestMatchers(HttpMethod.GET, "/books/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers(HttpMethod.POST, "/books/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers(HttpMethod.PUT, "/books/{bookId}").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers(HttpMethod.DELETE, "/books/{bookId}").hasAuthority("ROLE_ADMIN")

                // Allow public access to /auth endpoints (login, register, logout)
                .requestMatchers("/auth/**").permitAll()

                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic
                .authenticationEntryPoint((request, response, authException) -> {
                    // Suppress browser popup prompt
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader("WWW-Authenticate", ""); 
                })
            ) // Enable basic authentication with custom handling
            .logout(logout -> logout
                .logoutUrl("/auth/logout") // Endpoint to trigger logout
                .invalidateHttpSession(true) // Invalidate the session
                .clearAuthentication(true) // Clear authentication context
                .permitAll() // Allow everyone to call the logout endpoint
            )
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless session management for REST APIs
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findByUsername(username)
                .map(user -> (UserDetails) user)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
