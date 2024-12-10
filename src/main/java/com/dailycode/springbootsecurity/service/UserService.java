package com.dailycode.springbootsecurity.service;

import com.dailycode.springbootsecurity.entity.User;
import com.dailycode.springbootsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User verify(User user) {
        return userRepository.findByUsername(user.getUsername());
    }
}
