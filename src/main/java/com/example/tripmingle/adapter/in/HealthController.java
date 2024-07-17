package com.example.tripmingle.adapter.in;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "헬스체크")
@RestController
public class HealthController {

	@Operation(summary = "헬스체크")
	@GetMapping("/health")
	public String healthCheck() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return "health sucess " + email;
	}

	@Operation(summary = "cicd 작동 여부 확인을 위한 테스트")
	@GetMapping("/health/cicd")
	public String cicdCheck() {
		return "Wow 이게 되네";
	}

}
