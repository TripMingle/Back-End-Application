package com.example.tripmingle.adapter.out;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.S3Exception;
import com.example.tripmingle.port.out.S3StoragePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3StorageAdapter implements S3StoragePort {

	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxSizeString;

	@Override
	public String upload(MultipartFile image) {
		//입력받은 이미지 파일이 빈 파일인지 검증
		if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
			throw new S3Exception("empty file exception", ErrorCode.EMPTY_FILE_EXCEPTION);
		}
		//uploadImage를 호출하여 S3에 저장된 이미지의 public url을 반환한다.
		return this.uploadImage(image);
	}

	private String uploadImage(MultipartFile image) {
		this.validateImageFileExtention(image.getOriginalFilename());
		try {
			return this.uploadImageToS3(image);
		} catch (IOException e) {
			throw new S3Exception("image upload exception", ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
		}
	}

	private void validateImageFileExtention(String filename) {
		int lastDotIndex = filename.lastIndexOf(".");
		if (lastDotIndex == -1) {
			throw new S3Exception("no file extention", ErrorCode.NO_FILE_EXTENTION);
		}

		String extention = filename.substring(lastDotIndex + 1).toLowerCase();
		List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

		if (!allowedExtentionList.contains(extention)) {
			throw new S3Exception("invalid file extention", ErrorCode.INVALID_FILE_EXTENTION);
		}
	}

	private String uploadImageToS3(MultipartFile image) throws IOException {
		String originalFilename = image.getOriginalFilename(); //원본 파일 명
		String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

		String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

		InputStream is = image.getInputStream();
		byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

		ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
		metadata.setContentType("image/" + extention);
		metadata.setContentLength(bytes.length);

		//S3에 요청할 때 사용할 byteInputStream 생성
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

		try {
			//S3로 putObject 할 때 사용할 요청 객체
			//생성자 : bucket 이름, 파일 명, byteInputStream, metadata
			PutObjectRequest putObjectRequest =
				new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, metadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest); // put image to S3
		} catch (Exception e) {
			throw new S3Exception("put object exception", ErrorCode.PUT_OBJECT_EXCEPTION);
		} finally {
			byteArrayInputStream.close();
			is.close();
		}

		return amazonS3.getUrl(bucket, s3FileName).toString();
	}

	@Override
	public void deleteImageFromS3(String imageAddress) {
		String key = getKeyFromImageAddress(imageAddress);
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
		} catch (Exception e) {
			throw new S3Exception("image delete exception", ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
		}
	}

	private String getKeyFromImageAddress(String imageAddress) {
		try {
			URL url = new URL(imageAddress);
			String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
			return decodingKey.substring(1); // 맨 앞의 '/' 제거
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			throw new S3Exception("image delete exception", ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
		}
	}

}
