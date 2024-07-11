package com.example.tripmingle.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.res.country.GetContinentsResDTO;
import com.example.tripmingle.port.in.ContinentUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "대륙")
@RestController
@RequestMapping("/continent")
@RequiredArgsConstructor
public class ContinentController {
	private final ContinentUseCase continentUseCase;

	@Operation(summary = "대륙 조회")
	@GetMapping("")
	public ResponseEntity<ResultResponse> getContinents() {
		List<GetContinentsResDTO> getContinentsResDTOS = continentUseCase.getContinents();
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CONTINENT_SUCCESS, getContinentsResDTOS));
	}

}
