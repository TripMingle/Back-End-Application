package com.example.tripmingle.dto.res.posting;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOnePostingResDTO {

	private Long postingId;
	private String title;
	private String content;
	private String country;
	private LocalDateTime createAt;
	private Long userId;
	private String userImageUrl;
	private String userNickName;
	private String userAgeRange;
	private String userGender;
	private String userNationality;
	private String selfIntroduce;
	private boolean isMine;
	private double userTemperature;
	private boolean myLikeState;
	private int commentCount;
	private int likeCount;
	private List<GetOnePostingCommentsResDTO> postingComments;

}
