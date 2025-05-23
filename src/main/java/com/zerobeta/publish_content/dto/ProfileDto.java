package com.zerobeta.publish_content.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private Long id;
    private String displayName;
    private String bio;
    private String country;
}
