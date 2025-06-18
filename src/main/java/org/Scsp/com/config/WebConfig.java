package org.Scsp.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // ⚠️ dùng allowedOriginPatterns thay vì allowedOrigins
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // ⚠️ bắt buộc để cho phép gửi cookie/session
    }
}
