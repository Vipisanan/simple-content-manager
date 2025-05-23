package com.zerobeta.publish_content.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class CreateContentDto {

    private String writerEmail;
    private String title;
    private String details;
    private LocalDateTime datePublished;
    private LocalDateTime updatedAt;
    private Set<String> categoryNames;
}
