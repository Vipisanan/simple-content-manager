package com.zerobeta.publish_content.repository;

import com.zerobeta.publish_content.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}