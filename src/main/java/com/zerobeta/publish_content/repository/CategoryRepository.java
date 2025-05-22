package com.zerobeta.publish_content.repository;

import com.zerobeta.publish_content.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Category findByName(String name);
}