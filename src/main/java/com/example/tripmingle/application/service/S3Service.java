package com.example.tripmingle.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.port.out.S3StoragePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final S3StoragePort s3StoragePort;

	public String uploadImage(MultipartFile image) {
		return s3StoragePort.upload(image);
	}

	public void deleteCountryImage(String imageUrl) {
		s3StoragePort.deleteImageFromS3(imageUrl);
	}
}
