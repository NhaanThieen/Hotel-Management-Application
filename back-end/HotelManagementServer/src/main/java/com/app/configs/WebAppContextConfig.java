// Cấu hình những gì liên quan tới web
package com.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Khai báo đây là file cấu hình các Bean bằng code java, thay vì file .xml cũ. Spring sẽ đọc file này
@Configuration
// Đi vào từng file trong package để khởi tạo các Bean (Có annotation), sau đó bỏ vào Container
@ComponentScan(
        basePackages = {
            "com.app.controllers",}
)
// Hiện thực cấu hình mặc định của interface WebMvcConfigurer. Cần sửa cái nào thì ghi đè
@EnableWebMvc
// implements WebMvcConfigurer để ghi đè lại cấu hình webmvc của spring
public class WebAppContextConfig implements WebMvcConfigurer {

    // - Khi nhận 1 request đòi tài nguyên tĩnh như (như ảnh .png, file .css, file .js)
    // mà dispatcher không tìm thấy thì nó chuyển cái request đó về cho Default Servlet 
    // mặc định của Tomcat để nó tìm giúp.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // Spring MVC thuần thì File tĩnh được cấu hình không nằm ở trong folder resources. Nên cần phải cấu hình
    // lại cho trỏ về resources.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5173") // Cho phép React (3000) hoặc Vite (5173) truy cập
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Cho phép các hàm HTTP này
                .allowedHeaders("*") // Cho phép tất cả các header
                .allowCredentials(true); // Cho phép gửi token chéo domain
    }

    // Do request gửi lên là form đã được mã hóa để gửi file. Nên cần đối tượng này để giải mã (nếu không mọi dữ liệu sẽ là null).
    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
