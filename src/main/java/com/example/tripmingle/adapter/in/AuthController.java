package com.example.tripmingle.adapter.in;


import com.example.tripmingle.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    /*
    //회원가입
    public void signUp(){
        authUseCase.signUp();
    }


    //비밀번호 찾기 (이메일인증 또는 소셜인증 선행, 비밀번호 재설정)
    public void resetPassword(){
        authUseCase.resetPassword();
    }
    */
}
