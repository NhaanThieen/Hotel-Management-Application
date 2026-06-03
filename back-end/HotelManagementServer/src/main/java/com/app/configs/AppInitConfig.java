package com.app.configs;

import com.app.services.servicesImpl.SidebarService;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import java.util.ResourceBundle;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// Extends class này để báo cho Server (Tomcat) biết đây mới là file cấu hình hệ thống tạo Beans cho IoC Container, thay thế cho file .xml cũ
public class AppInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Nạp các cấu hình vào root context bao gồm: db, bảo mật, service, repository
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
            RootAppContextConfig.class,
            ThymeleafConfig.class,
            HibernateConfig.class,
            SpringSecurityConfig.class
        };
    }

    // Nạp các cấu hình vào servlet context bao gồm: controller
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
            WebAppContextConfig.class
        };
    }

    // Xác định loại URL nào sẽ do DispatcherServlet đứng ra chịu trách nhiệm quản lý
    @Override
    protected String[] getServletMappings() {
        return new String[]{
            "/"
        };
    }

    // Cấu hình để server cho phép resolver giải mã dữ liệu đã bị encrypt (mã hóa).Nhưng cho có bộ giải mã, phải cấu hình thêm bộ giải mã
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String location = "/";
        long maxFileSize = 5242880;
        long maxRequestSize = 20971520;
        int fileSizeThreshold = 0;

        registration.setMultipartConfig(new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold));
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        int timeout = 30;
        try {
            // Sử dụng ResourceBundle để đọc file properties trong classpath
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            timeout = Integer.parseInt(bundle.getString("session.timeout"));
        } catch (Exception e) {
            System.out.println("Không đọc được file config, dùng mặc định 30p");
        }
        servletContext.setSessionTimeout(timeout);
    }
}
