package com.zerobeta.publish_content.repository;

import com.zerobeta.publish_content.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}