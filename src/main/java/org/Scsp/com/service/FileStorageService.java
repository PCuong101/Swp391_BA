// src/main/java/org/Scsp/com/service/FileStorageService.java
package org.Scsp.com.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);
}