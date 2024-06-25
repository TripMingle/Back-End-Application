package com.example.tripmingle.dto.req.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConfirmUsersReqDTO {
    List<Long> userIds;
}
