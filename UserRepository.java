package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Return all users with the given username
    List<User> findByUsername(String username);

    // Optional: find first user by username
    default Optional<User> findFirstByUsername(String username) {
        List<User> users = findByUsername(username);
        if (users.isEmpty()) return Optional.empty();
        return Optional.of(users.get(0));
    }

    // Check if username exists (for registration)
    boolean existsByUsername(String username);
}
