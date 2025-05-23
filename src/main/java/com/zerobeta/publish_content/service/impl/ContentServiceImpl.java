package com.zerobeta.publish_content.service.impl;

import com.zerobeta.publish_content.dto.CategoryCreateRequest;
import com.zerobeta.publish_content.dto.ContentDto;
import com.zerobeta.publish_content.dto.CreateContentDto;
import com.zerobeta.publish_content.mapper.ContentMapper;
import com.zerobeta.publish_content.model.Category;
import com.zerobeta.publish_content.model.Content;
import com.zerobeta.publish_content.model.User;
import com.zerobeta.publish_content.repository.CategoryRepository;
import com.zerobeta.publish_content.repository.ContentRepository;
import com.zerobeta.publish_content.repository.UserRepository;
import com.zerobeta.publish_content.service.CategoryService;
import com.zerobeta.publish_content.service.ContentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ContentMapper contentMapper;
    private final CategoryService categoryService;


    @Override
    public ContentDto createContent(CreateContentDto createContentDto) {
        // Fetch writer (User) by email
        User writer = null;
        if (createContentDto.getWriterEmail() != null) {
            writer = userRepository.findByEmail(createContentDto.getWriterEmail());
            if (writer == null) {
                throw new IllegalArgumentException("Writer not found: " + createContentDto.getWriterEmail());
            }
        }

        // Fetch categories by name if not exist crete a category
        Set<Category> categories = Collections.emptySet();
        if (createContentDto.getCategoryNames() != null && !createContentDto.getCategoryNames().isEmpty()) {
//            categories = new HashSet<>(categoryRepository.findByNameIgnoreCase(createContentDto.getCategoryNames()));
//            if (categories.isEmpty()) {
//                createContentDto.getCategoryNames().forEach(category -> {
//                    CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(category);
//                    categoryService.createCategory(categoryCreateRequest);
//                });
//            }
            createContentDto.getCategoryNames().forEach(category -> {
                Optional<Category> existedCategory = categoryRepository.findByNameIgnoreCase(category);
                        if(!existedCategory.isPresent()) {
                            CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(category);
                            categoryService.createCategory(categoryCreateRequest);
                        }
            });
            categories = new HashSet<>(categoryRepository.findByNameIn(createContentDto.getCategoryNames()));
        }

        // Map to entity
        Content content = contentMapper.toEntity(createContentDto, writer, categories);

        // Save and map back to DTO
        Content saved = contentRepository.save(content);
        return contentMapper.toDto(saved);
    }

    @Override
    public Optional<ContentDto> getContentById(Long id) {
        return contentRepository.findById(id).map(contentMapper::toDto);
    }

    @Override
    public Page<ContentDto> getAllContents(Pageable pageable) {
        Page<Content> page = contentRepository.findAll(pageable);
        return new PageImpl<>(
                page.getContent().stream().map(contentMapper::toDto).collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public ContentDto updateContent(Long id, ContentDto contentDto) {
        Optional<Content> contentOpt = contentRepository.findById(id);
        if (contentOpt.isEmpty()) throw new RuntimeException("Content not found");
        Content content = contentOpt.get();

        content.setTitle(contentDto.getTitle());
        content.setDetails(contentDto.getDetails());
        content.setDatePublished(contentDto.getDatePublished());
        content.setUpdatedAt(contentDto.getUpdatedAt());

        if (contentDto.getWriterEmail() != null) {
            User writer = userRepository.findByEmail(contentDto.getWriterEmail());
            content.setWriter(writer);
        }
        if (contentDto.getCategoryNames() != null) {
            Set<Category> categories = contentDto.getCategoryNames().stream()
                    .map(categoryRepository::findByName)
                    .collect(Collectors.toSet());
            content.setCategories(categories);
        }

        Content updated = contentRepository.save(content);
        return contentMapper.toDto(updated);
    }

    @Override
    public void deleteContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Content not found with id: " + id));

        contentRepository.delete(content);
    }
}