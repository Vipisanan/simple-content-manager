package com.zerobeta.publish_content.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long contentId;
    private Long userId;
    private String userEmail; // Optional: For display
    private String text;
    private LocalDateTime createdAt;
}