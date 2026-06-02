
package com.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
// Hiện thực sẵn các bean cho spring security. Cần sửa dụng cái nào thì override lại bean đó
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    // Đối tượng hash password
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable()).authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/admin/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                // Cấp quyền truy cập công khai cho các thư mục tài nguyên tĩnh
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                // Bất kỳ URL nào không nằm trong danh sách thì phải login
                .anyRequest().authenticated()
        ).formLogin(form -> form.loginPage("/admin/login/") // Đường dẫn tới trang đăng nhập
                .loginProcessingUrl("/login") // Đường dẫn xử lý POST
                .defaultSuccessUrl("/admin/", true) // Chuyển hướng khi đăng nhập thành công
                .failureUrl("/admin/login/?error=true") // Chuyển hướng khi thất bại
                .permitAll()
        ).logout((logout) -> logout.logoutSuccessUrl("/admin/login/").permitAll())
                .sessionManagement(session -> session
                .invalidSessionUrl("/admin/login/?timeout=true") // Khi session hết hạn thì trở về trang login
                .maximumSessions(1) // Mỗi tài khoản chỉ được đăng nhập trên 1 thiết bị/browser cùng lúc.
                .expiredUrl("/admin/login/?expired=true") // Nếu người thứ 2 đăng nhập, người thứ nhất sẽ bị văng ra về điều hướng về login
                ); 
        return http.build();
    }
}
