package org.Scsp.com.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
