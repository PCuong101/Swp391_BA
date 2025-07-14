package org.Scsp.com.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.Scsp.com.Enum.CustomLogicKey;
import org.Scsp.com.dto.AchievementTemplateDto;
import org.Scsp.com.service.AchievementTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/achievement-templates")
@RequiredArgsConstructor
public class AchievementTemplateController {

    private final AchievementTemplateService service;

    // ✅ Create new achievement template
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AchievementTemplateDto> createWithIcon(
            @RequestPart("dto") @Valid AchievementTemplateDto dto,
            @RequestPart(value = "icon",required = false) MultipartFile iconFile) {
        AchievementTemplateDto created = service.create(dto,iconFile);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ✅ Get all templates
    @GetMapping
    public ResponseEntity<List<AchievementTemplateDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ✅ Get a template by ID
    @GetMapping("/{id}")
    public ResponseEntity<AchievementTemplateDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ✅ Update a template by ID
    @PutMapping("/{id}")
    public ResponseEntity<AchievementTemplateDto> update(@PathVariable Long id,
                                                         @Valid @RequestBody AchievementTemplateDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // ✅ Delete a template by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-logic")
    public ResponseEntity<List<CustomLogicKey>> getAllLogicTemplates() {
        return ResponseEntity.ok(Arrays.asList(CustomLogicKey.values()));
    }

}
