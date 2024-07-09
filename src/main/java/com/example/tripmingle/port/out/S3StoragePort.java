package com.example.tripmingle.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface S3StoragePort {
	public String upload(MultipartFile image);

	public void deleteImageFromS3(String imageAddress);
}
