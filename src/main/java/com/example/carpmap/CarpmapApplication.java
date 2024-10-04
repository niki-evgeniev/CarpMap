package com.example.carpmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarpmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarpmapApplication.class, args);
    }

}
