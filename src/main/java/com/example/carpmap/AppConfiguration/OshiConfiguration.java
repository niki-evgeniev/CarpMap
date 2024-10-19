package com.example.carpmap.AppConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;

@Configuration
public class OshiConfiguration {

    @Bean
    public SystemInfo systemInfo() {
        return new SystemInfo();
    }
}
