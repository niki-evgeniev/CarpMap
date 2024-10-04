package com.example.carpmap.AppConfiguration;

import com.example.carpmap.Interceptor.BannedUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final BannedUserInterceptor bannedUserInterceptor;

    public InterceptorConfiguration(BannedUserInterceptor bannedUserInterceptor) {
        this.bannedUserInterceptor = bannedUserInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(bannedUserInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
