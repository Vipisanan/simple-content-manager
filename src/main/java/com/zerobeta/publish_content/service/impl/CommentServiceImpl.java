package com.zerobeta.publish_content.service.impl;


import com.zerobeta.publish_content.dto.CommentCreateRequest;
import com.zerobeta.publish_content.dto.CommentDto;
import com.zerobeta.publish_content.dto.CommentUpdateRequest;
import com.zerobeta.publish_content.mapper.CommentMapper;
import com.zerobeta.publish_content.model.Comment;
import com.zerobeta.publish_content.model.Content;
import com.zerobeta.publish_content.model.User;
import com.zerobeta.publish_content.repository.CommentRepository;
import com.zerobeta.publish_content.repository.ContentRepository;
import com.zerobeta.publish_content.repository.UserRepository;
import com.zerobeta.publish_content.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    @Override
    public CommentDto createComment(CommentCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Content content = contentRepository.findById(request.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));

        Comment comment = commentMapper.toEntity(request, content, user);

        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public Optional<CommentDto> getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto);
    }

    @Override
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        commentMapper.updateEntity(request, comment);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
