package com.example.tripmingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TripMingleApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripMingleApplication.class, args);
    }

}
