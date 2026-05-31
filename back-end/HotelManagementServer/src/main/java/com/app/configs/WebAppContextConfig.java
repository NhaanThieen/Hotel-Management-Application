// Cấu hình những gì liên quan tới web
package com.app.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Khai báo đây là file cấu hình các Bean bằng code java, thay vì file .xml cũ
@Configuration
// Đi vào từng file trong package để khởi tạo các Bean (Có annotation), sau đó bỏ vào Container
@ComponentScan(
        basePackages = {
            "com.app.controllers",
            "com.app.repositories",
            "com.app.services",
            "com.app.dto"
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

    
    // Spring MVC thuần thì File tĩnh được cấu hình không nằm ở trong folder resources. Nên cần phải cấu hình
    // lại cho trỏ về resources.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }


    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "doa6ykcp1",
                        "api_key", "932522258646968",
                        "api_secret", "n4WGj7TbCtEqMDhwz1lf4irdNHk",
                        "secure", true));
        return cloudinary;
    }
    
    // Do request gửi lên là form đã được mã hóa để gửi file. Nên cần đối tượng này để giải mã (nếu không mọi dữ liệu sẽ là null).
    @Bean
    public StandardServletMultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();
    }
}
