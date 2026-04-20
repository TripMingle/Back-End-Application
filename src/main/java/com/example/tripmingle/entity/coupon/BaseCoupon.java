package com.example.tripmingle.entity.coupon;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseCoupon {

	private String eventName;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

}
