package com.example.tripmingle.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.etc.UpdateBoardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String title;

	@Column(columnDefinition="TEXT")
	private String content;

	private String continent;
	private String countryName;

	private String imageUrl;

	//여행 타입 list
	private String tripType;

	private double preferGender; // 선호 성별
	private double preferSmoking; // 선호 흡연타입
	private double preferShopping; // 선호 활동 - 쇼핑
	private double preferInstagramPicture; // 선호 활동 - 인스타사진
	private double preferDrink; // 선호 활동 - 음주

	//인원수
	private int currentCount;
	private int maxCount;

	private LocalDate startDate;
	private LocalDate endDate;

	private String language;

	private int commentCount;
	private int likeCount;
	private int bookMarkCount;

	public void increaseLikeCount() {
		likeCount++;
	}

	public void decreaseLikeCount(int diff) {
		likeCount -= diff;
	}

	public void increaseBookMarkCount() {
		bookMarkCount++;
	}

	public void decreaseBookMarkCount(int diff) {
		bookMarkCount -= diff;
	}

	public void increaseCommentCount() {
		this.commentCount++;
	}

	public void decreaseCommentCount(int diff) {
		this.commentCount -= diff;
	}

	public void update(UpdateBoardDTO updateBoardDTO) {
		if (updateBoardDTO.getTitle() != null) {
			this.title = updateBoardDTO.getTitle();
		}
		if (updateBoardDTO.getContent() != null) {
			this.content = updateBoardDTO.getContent();
		}
		if (updateBoardDTO.getTripType() != null) {
			this.tripType = updateBoardDTO.getTripType();
		}
		this.preferGender = updateBoardDTO.getPreferGender();
		this.preferSmoking = updateBoardDTO.getPreferSmoking();
		this.preferShopping = updateBoardDTO.getPreferShopping();
		this.preferInstagramPicture = updateBoardDTO.getPreferInstagramPicture();
		this.preferDrink = updateBoardDTO.getPreferDrink();

		if (updateBoardDTO.getMaxCount() > 0) {
			this.maxCount = updateBoardDTO.getMaxCount();
		}
		if (updateBoardDTO.getStartDate() != null) {
			this.startDate = updateBoardDTO.getStartDate();
		}
		if (updateBoardDTO.getEndDate() != null) {
			this.endDate = updateBoardDTO.getEndDate();
		}
		if (updateBoardDTO.getLanguage() != null) {
			this.language = updateBoardDTO.getLanguage();
		}
	}
}
