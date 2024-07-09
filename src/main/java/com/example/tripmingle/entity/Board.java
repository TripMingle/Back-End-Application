package com.example.tripmingle.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Where;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.etc.UpdateBoardDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String content;

	private String continent;
	private String countryName;

	private String imageUrl;

	private double preferGender; // 선호 성별
	private double preferSmoking; // 선호 흡연타입
	private double preferActivity; // 선호 활동 - 액티비티
	private double preferInstagramPicture; // 선호 활동 - 인스타사진
	private double preferFoodExploration; // 선호 활동 - 맛집탐방
	private double preferAdventure; // 선호 활동 - 탐험

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
		if (updateBoardDTO.getContinent() != null) {
			this.continent = updateBoardDTO.getContinent();
		}
		if (updateBoardDTO.getCountryName() != null) {
			this.countryName = updateBoardDTO.getCountryName();
		}
        /*
            if (updateBoardDTO.getPersonalType() != null) {
            this.personalType = updateBoardDTO.getPersonalType();
        }
        if (updateBoardDTO.getTripType() != null) {
            this.tripType = updateBoardDTO.getTripType();
        }
        */
		this.preferGender = updateBoardDTO.getPreferGender();
		this.preferSmoking = updateBoardDTO.getPreferSmoking();
		this.preferActivity = updateBoardDTO.getPreferActivity();
		this.preferInstagramPicture = updateBoardDTO.getPreferInstagramPicture();
		this.preferFoodExploration = updateBoardDTO.getPreferFoodExploration();
		this.preferAdventure = updateBoardDTO.getPreferAdventure();

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
