package com.app.configs;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// Extends class này để báo cho Server biết đây mới là file cấu hình hệ thống tạo Beans cho IoC Container, thay thế cho file .xml cũ
public class AppInitConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Sẽ gọi chạy các file cấu hình khác được khai báo bên trong.
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
            ThymeleafConfig.class,
            HibernateConfig.class
        };
    }

    // Sẽ gọi chạy các file cấu hình khác được khai báo bên trong.
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
}
