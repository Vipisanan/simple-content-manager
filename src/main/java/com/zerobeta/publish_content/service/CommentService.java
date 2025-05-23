package com.zerobeta.publish_content.service;

import com.zerobeta.publish_content.dto.CommentCreateRequest;
import com.zerobeta.publish_content.dto.CommentDto;
import com.zerobeta.publish_content.dto.CommentUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto createComment(CommentCreateRequest request);

    Optional<CommentDto> getCommentById(Long id);

    List<CommentDto> getAllComments();

    CommentDto updateComment(Long id, CommentUpdateRequest request);

    void deleteComment(Long id);
}
