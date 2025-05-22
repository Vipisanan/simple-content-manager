package com.zerobeta.publish_content.mapper;

import com.zerobeta.publish_content.dto.ContentDto;
import com.zerobeta.publish_content.model.Category;
import com.zerobeta.publish_content.model.Content;
import com.zerobeta.publish_content.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ContentMapper {

    // Entity → DTO
    public ContentDto toDto(Content content) {
        if (content == null) return null;
        ContentDto dto = new ContentDto();
        dto.setId(content.getId());
        dto.setWriterName(content.getWriter() != null ? content.getWriter().getEmail() : null);
        dto.setTitle(content.getTitle());
        dto.setDetails(content.getDetails());
        dto.setDatePublished(content.getDatePublished());
        dto.setUpdatedAt(content.getUpdatedAt());
        dto.setCategoryNames(content.getCategories() != null ? content.getCategories().stream().map(Category::getName).collect(Collectors.toSet()) : Collections.emptySet());
        return dto;
    }

    // DTO → Entity (expects User and Set<Category> to be provided)
    public Content toEntity(ContentDto dto, User writer, Set<Category> categories) {
        if (dto == null) return null;
        Content content = new Content();
        content.setId(dto.getId());
        content.setTitle(dto.getTitle());
        content.setDetails(dto.getDetails());
        content.setDatePublished(dto.getDatePublished());
        content.setUpdatedAt(dto.getUpdatedAt());
        content.setWriter(writer);
        content.setCategories(categories);
        return content;
    }

    // List<Entity> → List<DTO>
    public List<ContentDto> toDtoList(List<Content> contents) {
        if (contents == null) return Collections.emptyList();
        return contents.stream().map(this::toDto).collect(Collectors.toList());
    }

    // List<DTO> → List<Entity> (expects maps for writers and categories)
    public List<Content> toEntityList(List<ContentDto> dtos, java.util.Map<String, User> writerMap, java.util.Map<String, Category> categoryMap) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(dto -> {
            User writer = dto.getWriterName() != null ? writerMap.get(dto.getWriterName()) : null;
            Set<Category> categories = dto.getCategoryNames() != null ? dto.getCategoryNames().stream().map(categoryMap::get).filter(Objects::nonNull).collect(Collectors.toSet()) : Collections.emptySet();
            return toEntity(dto, writer, categories);
        }).collect(Collectors.toList());
    }
}