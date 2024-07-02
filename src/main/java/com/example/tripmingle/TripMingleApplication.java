package com.example.tripmingle;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TripMingleApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
        System.out.println("Database Username: " + dotenv.get("DATABASE_NAME"));
        System.out.println("Database Password: " + dotenv.get("DATABASE_PASSWORD"));
        System.out.println("JWT Secret Key: " + dotenv.get("JWT_SECRET_KEY"));
        System.out.println("Kakao Rest API Key: " + dotenv.get("KAKAO_REST_API_KEY"));
        System.out.println("Kakao Client Secret: " + dotenv.get("KAKAO_CLIENT_SECRET"));
        SpringApplication.run(TripMingleApplication.class, args);
        System.out.println("Database Username: " + dotenv.get("DATABASE_NAME"));
        System.out.println("Database Password: " + dotenv.get("DATABASE_PASSWORD"));
        System.out.println("JWT Secret Key: " + dotenv.get("JWT_SECRET_KEY"));
        System.out.println("Kakao Rest API Key: " + dotenv.get("KAKAO_REST_API_KEY"));
        System.out.println("Kakao Client Secret: " + dotenv.get("KAKAO_CLIENT_SECRET"));
    }

}
