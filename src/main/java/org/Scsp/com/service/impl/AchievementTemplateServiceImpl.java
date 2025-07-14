package org.Scsp.com.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.Scsp.com.dto.AchievementTemplateDto;
import org.Scsp.com.model.AchievementTemplate;
import org.Scsp.com.repository.AchievementTemplateRepository;
import org.Scsp.com.service.AchievementTemplateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementTemplateServiceImpl implements AchievementTemplateService {

    private final AchievementTemplateRepository repository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public AchievementTemplateDto create(AchievementTemplateDto dto, MultipartFile iconFile) {
        String fileName = null;

        if (iconFile != null && !iconFile.isEmpty()) {
            try {
                Files.createDirectories(Paths.get(uploadDir));
                fileName = UUID.randomUUID() + "_" + iconFile.getOriginalFilename();
                Path path = Paths.get(uploadDir, fileName);
                Files.copy(iconFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Upload failed", e);
            }
        }

        AchievementTemplate entity = toEntity(dto);
        entity.setIconUrl(fileName!= null ? uploadDir + "/" + fileName : null);
        AchievementTemplate saved = repository.save(entity);
        return toDto(saved);
    }

    @Override
    public List<AchievementTemplateDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AchievementTemplateDto findById(Long id) {
        AchievementTemplate entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Template not found with id: " + id));
        return toDto(entity);
    }

    @Override
    public AchievementTemplateDto update(Long id, AchievementTemplateDto dto) {
        AchievementTemplate entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Template not found with id: " + id));
        updateEntityFromDto(dto, entity);
        AchievementTemplate updated = repository.save(entity);
        return toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Template not found with id: " + id);
        }
        repository.deleteById(id);
    }

    // -------------------- Mapper Methods --------------------

    private AchievementTemplate toEntity(AchievementTemplateDto dto) {
        AchievementTemplate entity = new AchievementTemplate();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setCustomLogicKey(dto.getCustomLogicKey());
        entity.setThreshold(dto.getThreshold());
        entity.setVisible(dto.getVisible());
        return entity;
    }

    private void updateEntityFromDto(AchievementTemplateDto dto, AchievementTemplate entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setCustomLogicKey(dto.getCustomLogicKey());
        entity.setThreshold(dto.getThreshold());
        entity.setVisible(dto.getVisible());
    }

    private AchievementTemplateDto toDto(AchievementTemplate entity) {
        AchievementTemplateDto dto = new AchievementTemplateDto();
        dto.setTemplateID(entity.getTemplateID());
        dto.setTitle(entity.getTitle());
        dto.setIconUrl(entity.getIconUrl());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setCustomLogicKey(entity.getCustomLogicKey());
        dto.setThreshold(entity.getThreshold());
        dto.setVisible(entity.getVisible());
        return dto;
    }
}
