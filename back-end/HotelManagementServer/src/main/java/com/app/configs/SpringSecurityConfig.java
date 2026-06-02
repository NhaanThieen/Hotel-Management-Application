package com.app.configs;

import com.app.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
// Hiện thực sẵn các bean cho spring security. Cần sửa dụng cái nào thì override lại bean đó
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    // Này để xác thực cho đăng nhập bằng API, Token    
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**") 
                .csrf(c -> c.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/login").permitAll()
                    .requestMatchers("/api/secure/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/secure/**").authenticated()
                    .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    // Này để xác thực cho đăng nhập bằng thymeleaf    
    @Bean
    @Order(2)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                    .requestMatchers("/", "/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/admin/login/")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/admin/", true)
                    .failureUrl("/admin/login/?error=true")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/admin/login/")
                    .permitAll()
                )
                .sessionManagement(session -> session
                    .invalidSessionUrl("/admin/login/?timeout=true")
                    .maximumSessions(1)
                    .expiredUrl("/admin/login/?expired=true")
                );

        return http.build();
    }
}