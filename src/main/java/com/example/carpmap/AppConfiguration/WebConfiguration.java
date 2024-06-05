package com.example.carpmap.AppConfiguration;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean<CloudflareRealIpFilter> loggingFilter() {
        FilterRegistrationBean<CloudflareRealIpFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CloudflareRealIpFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
