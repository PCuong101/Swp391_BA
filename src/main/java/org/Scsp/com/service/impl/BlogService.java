package org.Scsp.com.service.impl;

import org.Scsp.com.dto.BlogDto;

import java.util.List;

public interface BlogService {
    BlogDto create(BlogDto dto, long userId);
    List<BlogDto> findAll();
}
