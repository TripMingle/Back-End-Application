package com.example.tripmingle.dto.req.user;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchUserMyPageReqDTO {

	private String name;
	private String gender;
	private LocalDate birthDay;
	private String userNickName;
	private String nationality;
	private String phoneNumber;
	private String selfIntroduction;

}
