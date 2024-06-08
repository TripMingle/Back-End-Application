package com.example.tripmingle.adapter.in;


import com.example.tripmingle.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    /*
    회원가입 : 리턴할게 없을때 (텍스트뿐일때)
    엑세스토큰 : 헤더에 넣어야할때
    아이디찾기 : 리턴할게 있을때


    @Operation(summary = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<ResultResponse> signUp(@RequestBody SignUpDto signUpDto){
        authService.signUp(signUpDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SIGNIN_SUCCESS));
    }

    @Operation(summary = "엑세스토큰 재발급")
    @PostMapping("/accesstoken")
    @Parameter(name = "AuthorizationRefresh", description = "AuthorizationRefresh", required = true, in = ParameterIn.HEADER)
    public ResponseEntity<ResultResponse> generateNewAccessToken(@RequestHeader(value="AuthorizationRefresh") String refreshToken){
        HttpHeaders headers = authService.generateNewAccessToken(refreshToken);
        return ResponseEntity.ok().headers(headers).body(ResultResponse.of(ResultCode.NEW_ACCESS_TOKEN_SUCCESS));
    }

    Operation(summary = "아이디 찾기")
    @PostMapping("/find-id")
    public ResponseEntity<FindIdResponse> findLoginId(@RequestBody FindIdDto findIdDto){
        String loginId = authService.findLoginId(findIdDto);
        return ResponseEntity.ok(FindIdResponse.of(ResultCode.FIND_LOGINID_SUCCESS, loginId));
    }
    */

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
