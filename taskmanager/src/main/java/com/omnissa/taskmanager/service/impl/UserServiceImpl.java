package com.omnissa.taskmanager.service.impl;

import com.omnissa.taskmanager.model.User;
import com.omnissa.taskmanager.repository.UserRepository;
import com.omnissa.taskmanager.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(String username, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, hashedPassword, "ROLE_USER");
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
