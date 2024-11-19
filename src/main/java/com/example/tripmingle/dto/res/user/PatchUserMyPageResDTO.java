package com.example.tripmingle.dto.res.user;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatchUserMyPageResDTO {

	private Long userId;
	private String name;
	private String gender;
	private LocalDate birthDay;
	private String phoneNumber;
	private String nickName;
	private String nationality;
	private String selfIntroduction;

}
