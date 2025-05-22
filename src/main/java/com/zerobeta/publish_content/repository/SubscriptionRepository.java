package com.zerobeta.publish_content.repository;

import com.zerobeta.publish_content.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}