package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository) {
        return args -> {
            // Check if admin already exists
            if (userRepository.findByUsername("admin") == null) {
                // Create admin user
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // Plain text for testing; hash for production

                // Assign ADMIN role
                Set<String> roles = new HashSet<>();
                roles.add("ADMIN");
                admin.setRoles(roles);

                // Save to database
                userRepository.save(admin);

                System.out.println("✅ Admin user created: username=admin, password=admin123");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
