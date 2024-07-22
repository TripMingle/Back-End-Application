package com.example.tripmingle.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.port.out.ImageStoragePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final ImageStoragePort imageStoragePort;

	public String uploadImage(MultipartFile image) {
		return imageStoragePort.upload(image);
	}

	public void deleteCountryImage(String imageUrl) {
		imageStoragePort.deleteImage(imageUrl);
	}
}
