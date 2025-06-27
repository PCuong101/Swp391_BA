package org.Scsp.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;
    private String title;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;

}
