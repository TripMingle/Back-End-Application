package com.example.tripmingle.dto.req;

import com.example.tripmingle.entity.PostingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllPostingsReqDTO {

    private String country;
    private PostingType postingType;

}
