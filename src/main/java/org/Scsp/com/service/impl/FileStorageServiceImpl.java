// src/main/java/org/Scsp/com/service/impl/FileStorageServiceImpl.java
package org.Scsp.com.service.impl;

import org.Scsp.com.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl() {
        // Định nghĩa đường dẫn đến thư mục lưu trữ
        this.fileStorageLocation = Paths.get("user-uploads")
                .toAbsolutePath().normalize();

        try {
            // Tạo thư mục nếu nó chưa tồn tại
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        // 1. Chuẩn hóa tên file
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // 2. Kiểm tra các ký tự không hợp lệ
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            // 3. Tạo tên file duy nhất để tránh trùng lặp
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch(Exception e) {
                // Không có đuôi file
            }
            String generatedFileName = UUID.randomUUID().toString().replace("-", "") + fileExtension;


            // 4. Copy file vào thư mục lưu trữ (ghi đè nếu file đã tồn tại)
            Path targetLocation = this.fileStorageLocation.resolve(generatedFileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return generatedFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }
}