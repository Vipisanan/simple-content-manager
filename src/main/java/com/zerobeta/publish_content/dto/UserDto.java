package com.zerobeta.publish_content.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private ProfileDto profile;
    private boolean canPublish;
}
