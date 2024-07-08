package com.example.tripmingle.adapter.in;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Operation(summary = "API 헬스 체크")
    @GetMapping("/health")
    public String healthCheck() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return "health sucess " + email;
    }

}
