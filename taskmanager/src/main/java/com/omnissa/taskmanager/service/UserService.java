package com.omnissa.taskmanager.service;

import com.omnissa.taskmanager.model.User;

import java.util.Optional;

public interface UserService {

    User register(String username, String rawPassword);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
