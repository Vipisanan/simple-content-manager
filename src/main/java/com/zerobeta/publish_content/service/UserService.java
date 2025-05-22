package com.zerobeta.publish_content.service;

import com.zerobeta.publish_content.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    void deleteUser(Long id);
}