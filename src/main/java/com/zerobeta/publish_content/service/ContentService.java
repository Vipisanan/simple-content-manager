package com.zerobeta.publish_content.service;

import com.zerobeta.publish_content.dto.ContentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContentService {
    ContentDto createContent(ContentDto contentDto);

    Optional<ContentDto> getContentById(Long id);

    Page<ContentDto> getAllContents(Pageable pageable);

    ContentDto updateContent(Long id, ContentDto contentDto);

    void deleteContent(Long id);
}