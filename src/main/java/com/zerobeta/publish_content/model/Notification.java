package com.zerobeta.publish_content.model;

import com.zerobeta.publish_content.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isRead = false;
}