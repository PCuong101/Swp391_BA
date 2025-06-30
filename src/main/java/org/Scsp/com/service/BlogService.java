package org.Scsp.com.service;

import org.Scsp.com.Enum.BlogStatus;
import org.Scsp.com.dto.BlogDto;

import java.util.List;

public interface BlogService {
    BlogDto create(BlogDto dto, long userId);
    List<BlogDto> findAll();
    void updateStatus(Long id, BlogStatus status);
    List<BlogDto> findByStatus(BlogStatus status);
}
