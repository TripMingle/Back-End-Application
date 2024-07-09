package com.example.tripmingle.dto.req.country;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadCountryImageReqDTO {
	private String countryName;
	private MultipartFile image;
}
