package com.zerobeta.publish_content.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentUpdateRequest {
    @NotBlank(message = "Text is required")
    private String text;
}
