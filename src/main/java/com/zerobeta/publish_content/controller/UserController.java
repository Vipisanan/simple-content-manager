package com.zerobeta.publish_content.controller;

import com.zerobeta.publish_content.dto.ProfileDto;
import com.zerobeta.publish_content.dto.ProfileUpdateRequest;
import com.zerobeta.publish_content.dto.UserCreateRequest;
import com.zerobeta.publish_content.dto.UserDto;
import com.zerobeta.publish_content.mapper.UserMapper;
import com.zerobeta.publish_content.model.User;
import com.zerobeta.publish_content.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserCreateRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        User user = UserMapper.toUser(request);
        User created = userService.registerUser(user);
        return ResponseEntity.ok(UserMapper.toDto(created));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> dtos = UserMapper.toDtoList(users);
        return ResponseEntity.ok(dtos);
    }

    // Get a user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get a user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDto(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Update profile for a user (since email/password cannot be updated)
    @PutMapping("/{id}/profile")
    public ResponseEntity<ProfileDto> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody ProfileUpdateRequest request) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        if (user.getProfile() == null) {
            return ResponseEntity.badRequest().build();
        }
        UserMapper.updateProfileFromDto(request, user.getProfile());
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(UserMapper.toDto(updatedUser.getProfile()));
    }
}