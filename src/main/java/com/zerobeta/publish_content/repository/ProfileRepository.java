package com.zerobeta.publish_content.repository;

import com.zerobeta.publish_content.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}