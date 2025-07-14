package org.Scsp.com.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.Scsp.com.Enum.CustomLogicKey;


@Getter
@Setter
public class AchievementTemplateDto {
    private Long templateID;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String category;

    @NotNull
    private CustomLogicKey customLogicKey;

    @NotNull
    @Min(0)
    private Integer threshold;

    private String iconUrl;
    private Boolean visible = true;
}
