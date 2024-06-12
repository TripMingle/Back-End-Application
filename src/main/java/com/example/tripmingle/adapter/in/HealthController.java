package com.example.tripmingle.adapter.in;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return "health sucess " + email;
    }

}
