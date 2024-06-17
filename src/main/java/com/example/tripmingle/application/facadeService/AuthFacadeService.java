package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.AuthService;
import com.example.tripmingle.dto.req.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.ValidateDuplicationResDTO;
import com.example.tripmingle.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacadeService implements AuthUseCase {

    private final AuthService authService;

    @Override
    public ValidateDuplicationResDTO validateDuplication(ValidateDuplicationReqDTO validateDuplicationReqDTO) {
        return authService.validateDuplication(validateDuplicationReqDTO);
    }
}
