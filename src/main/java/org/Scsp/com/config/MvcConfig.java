// src/main/java/org/Scsp/com/config/MvcConfig.java
package org.Scsp.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Lấy đường dẫn tuyệt đối đến thư mục user-uploads
        Path uploadDir = Paths.get("./user-uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Cấu hình resource handler
        // Khi client request đến /user-uploads/**
        // Spring sẽ tìm file trong thư mục `user-uploads` trên ổ đĩa
        registry.addResourceHandler("/user-uploads/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}