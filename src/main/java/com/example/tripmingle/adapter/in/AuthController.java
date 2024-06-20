package com.example.tripmingle.adapter.in;


import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.auth.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;
import com.example.tripmingle.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tripmingle.common.result.ResultCode.VALIDATE_COMPLETE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    @GetMapping("/validate/duplication")
    public ResponseEntity<ResultResponse> validateDuplication(@RequestBody ValidateDuplicationReqDTO validateDuplicationReqDTO) {
        ValidateDuplicationResDTO validateDuplicationResDTO = authUseCase.validateDuplication(validateDuplicationReqDTO);
        return ResponseEntity.ok().body(ResultResponse.of(VALIDATE_COMPLETE, validateDuplicationResDTO));
    }

}
