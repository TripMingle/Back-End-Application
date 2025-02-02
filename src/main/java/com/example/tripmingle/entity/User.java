package com.example.tripmingle.entity;

import java.time.LocalDate;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String role;

	@Column(nullable = false)
	private String loginType;

	@Column(nullable = false)
	private String oauthId;

	@Column(nullable = false, unique = true)
	private String nickName;

	@Column(nullable = false)
	private String ageRange;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String nationality;

	@Column(nullable = true)
	private String selfIntroduction;

	@Column(nullable = false, unique = true)
	private String phoneNumber;

	@Column(nullable = true)
	private String userImageUrl;

	@Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 3.0")
	private double userScore;

	@Column(nullable = false, columnDefinition = "DATE DEFAULT '1900-01-01'")
	private LocalDate birthDay;

	@Builder(toBuilder = true)
	public User(String email, String password, String role, String loginType, String oauthId, String nickName,
		String ageRange, String gender, String name, String nationality, String selfIntroduction, String phoneNumber,
		LocalDate birthDay) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.loginType = loginType;
		this.oauthId = oauthId;
		this.nickName = nickName;
		this.ageRange = ageRange;
		this.gender = gender;
		this.name = name;
		this.nationality = nationality;
		this.selfIntroduction = selfIntroduction;
		this.phoneNumber = phoneNumber;
		this.birthDay = birthDay;
	}

	public void updateUserMyPage(PatchUserMyPageReqDTO patchUserMyPageReqDTO) {
		this.name = patchUserMyPageReqDTO.getName();
		this.gender = patchUserMyPageReqDTO.getGender();
		this.birthDay = patchUserMyPageReqDTO.getBirthDay();
		this.nickName = patchUserMyPageReqDTO.getUserNickName();
		this.nationality = patchUserMyPageReqDTO.getNationality();
		this.phoneNumber = patchUserMyPageReqDTO.getPhoneNumber();
		this.selfIntroduction = patchUserMyPageReqDTO.getSelfIntroduction();
	}

	public void uploadUserImage(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

}
