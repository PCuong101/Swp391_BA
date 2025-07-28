package org.Scsp.com.service;

import org.Scsp.com.dto.AchievementTemplateDto;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface AchievementTemplateService {
    AchievementTemplateDto create(AchievementTemplateDto dto, MultipartFile iconFile);
    List<AchievementTemplateDto> findAll();
    AchievementTemplateDto findById(Long id);
    AchievementTemplateDto update(Long id, AchievementTemplateDto dto);
    void delete(Long id);

    void deleteAchievementTemplate(Long id);
}
