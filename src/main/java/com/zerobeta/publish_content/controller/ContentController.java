package com.zerobeta.publish_content.controller;

import com.zerobeta.publish_content.dto.ContentDto;
import com.zerobeta.publish_content.dto.PagedResponse;
import com.zerobeta.publish_content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public ResponseEntity<ContentDto> createContent(@RequestBody ContentDto contentDto) {
        ContentDto created = contentService.createContent(contentDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDto> getContent(@PathVariable Long id) {
        Optional<ContentDto> content = contentService.getContentById(id);
        return ResponseEntity.ok(content.get());
    }


    @GetMapping
    public ResponseEntity<PagedResponse<ContentDto>> getAllContents(@PageableDefault(size = 20) Pageable pageable) {
        Page<ContentDto> page = contentService.getAllContents(pageable);
        PagedResponse<ContentDto> response = PagedResponse.<ContentDto>builder()
                .data(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDto> updateContent(@PathVariable Long id, @RequestBody ContentDto contentDto) {
        ContentDto updated = contentService.updateContent(id, contentDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
}