package com.example.paperplane.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${S3_BUCKET_NAME:paperplane-s3-bucket}")
    private String bucketName;
    //testdd


    public String uploadFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFileName;

        try {

            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }

        return getFileUrl(fileName);
    }


    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(bucketName, fileName).toString();
    }


    public byte[] downloadFile(String fileUrl) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            String fileKey = extractFileKeyFromUrl(fileUrl);

            amazonS3.getObject(new GetObjectRequest(bucketName, fileKey))
                    .getObjectContent()
                    .transferTo(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from S3", e);
        }
    }


    private String extractFileKeyFromUrl(String fileUrl) {
        try {
            String baseUrl = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, amazonS3.getRegionName());

            if (!fileUrl.startsWith(baseUrl)) {
                throw new IllegalArgumentException("Invalid file URL: " + fileUrl);
            }

            String fileKey = fileUrl.substring(baseUrl.length());
            fileKey = URLDecoder.decode(fileKey, StandardCharsets.UTF_8.toString());

            return fileKey;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract file key from URL: " + fileUrl, e);
        }
    }
}
