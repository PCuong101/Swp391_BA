// src/main/java/org/Scsp/com/controller/FileUploadController.java
package org.Scsp.com.controller;

import lombok.AllArgsConstructor;
import org.Scsp.com.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    // Đây chính là endpoint mà frontend cần: POST /api/files/upload
    // Nó sẽ trả về JSON chứa URL của ảnh
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // Lưu file và lấy tên file duy nhất đã được tạo
        String fileName = fileStorageService.storeFile(file);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user-uploads/")
                .path(fileName)
                .toUriString();

        // Trả về JSON theo đúng định dạng mà frontend mong đợi
        return ResponseEntity.ok(Map.of("url", fileDownloadUri));
    }
}