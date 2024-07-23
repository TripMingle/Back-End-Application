package com.example.tripmingle.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoragePort {
	public String upload(MultipartFile image);

	public void deleteImage(String imageAddress);
}
