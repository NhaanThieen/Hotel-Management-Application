package com.app.configs;

import com.app.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

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

    // Sử dụng đối tượng này để tự động get user lên và kiểm tra mật khẩu thay vì phải làm thủ công.
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    // Này để xác thực cho đăng nhập bằng API, Token    
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        // Chỉ những url có /api/*** mới được vào bộ lọc này
        http.cors(Customizer.withDefaults())
                .securityMatcher("/api/**")
                .csrf(c -> c.disable())
                // Yêu cầu spring không được lưu session vào bộ nhớ http session của Tomcat. Tránh tràn ram, do mỗi lần request là tạo mới
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Phân quyền truy vập
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login", "/api/register").permitAll()
                // Mọi api bắt đầu bằng /api/** đã xác thực mới được vào
                .requestMatchers("/api/secure/**").authenticated()
                .anyRequest().permitAll()
                )
                // Thêm filter để phân giải token, sau đó tạo thành object authentication
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
                .anyRequest().authenticated())
                .formLogin(form -> form
                .loginPage("/admin/login/") // URL tới trang login
                .loginProcessingUrl("/login") // URL process login
                .defaultSuccessUrl("/admin/", true) // Nếu thành công thì chuyển về admin
                .failureUrl("/admin/login/?error=true") // Nếu thất bại thì về URL trang login
                .permitAll())
                .logout(logout -> logout
                .logoutSuccessUrl("/admin/login/") // Logout thành công thì về trang login
                .permitAll())
                .sessionManagement(session -> session
                .invalidSessionUrl("/admin/login/?timeout=true") // Chuyển hướng về login khi SessionId hết hạn
                .maximumSessions(1) // 1 tài khoản chỉ được đăng nhập 1 máy/browser
                .expiredUrl("/admin/login/?expired=true")); // Nếu máy khác đăng nhập cùng tài khoản thì chuyển hướng máy cũ

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Cho phép 2 cổng React và Vite
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173"));
        // CHÚ Ý: Bắt buộc phải có OPTIONS để pass qua Preflight Request
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Áp dụng cấu hình này cho mọi API
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
