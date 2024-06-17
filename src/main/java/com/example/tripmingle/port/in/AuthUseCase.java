package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.ValidateDuplicationResDTO;

public interface AuthUseCase {

    ValidateDuplicationResDTO validateDuplication(ValidateDuplicationReqDTO validateDuplicationReqDTO);

}
