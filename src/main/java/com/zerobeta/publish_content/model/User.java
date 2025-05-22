package com.zerobeta.publish_content.model;

import com.zerobeta.publish_content.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_user") // Table name for PostgreSQL
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
}