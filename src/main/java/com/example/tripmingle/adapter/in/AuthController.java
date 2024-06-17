package com.example.tripmingle.adapter.in;


import com.example.tripmingle.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;


}
