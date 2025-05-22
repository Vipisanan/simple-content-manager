package com.zerobeta.publish_content.repository;

import com.zerobeta.publish_content.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}