package com.mak.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/", "/auth/authenticate", "/auth/logout", "/auth/refresh-token",
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui/**",
                            "/webjars/**",
                            "/swagger-ui.html").permitAll();
                    // auth.requestMatchers("/auth/**").hasAnyRole("ADMIN", "USER");
                    auth.requestMatchers("/api/v1/**").hasAuthority("ROLE_USER");
                    auth.requestMatchers("/admin/v1/**").hasAuthority("ROLE_ADMIN");
                    auth.anyRequest().authenticated();

                })

                // .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(),
                // MANAGER.name())

                // .requestMatchers(GET,
                // "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(),
                // MANAGER_READ.name())
                // .requestMatchers(POST, "/api/v1/management/**")
                // .hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                // .requestMatchers(PUT, "/api/v1/management/**")
                // .hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                // .requestMatchers(DELETE, "/api/v1/management/**")
                // .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                /*
                 * .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                 * 
                 * .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                 * .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                 * .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                 * .requestMatchers(DELETE,
                 * "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                 */

                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
