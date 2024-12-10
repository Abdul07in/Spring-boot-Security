package com.dailycode.springbootsecurity.repository;

import com.dailycode.springbootsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
