// Cấu hình những gì liên quan tới web
package com.app.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Khai báo đây là file cấu hình các Bean bằng code java, thay vì file .xml cũ
@Configuration
// Đi vào từng file trong package để khởi tạo các Bean (Có annotation), sau đó bỏ vào Container
@ComponentScan(
        basePackages = {
            "com.app.controllers",
            "com.app.repositories",
            "com.app.services"

        }
)

// Cho phép sử dụng TransactionManager của Spring
@EnableTransactionManagement

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
}
