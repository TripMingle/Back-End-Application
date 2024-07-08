package com.example.tripmingle.adapter.in;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "헬스체크")
@RestController
public class HealthController {

	@GetMapping("/health")
	public String healthCheck() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return "health sucess " + email;
	}

}
