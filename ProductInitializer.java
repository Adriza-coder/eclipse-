package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ProductInitializer {

    @Bean
    CommandLineRunner insertSampleProducts(ProductRepository productRepository) {
        return args -> {

            // List of sample products
            List<Product> sampleProducts = Arrays.asList(
                    new Product("Laptop", "High-end gaming laptop", 1500.0, 5),
                    new Product("Phone", "Latest smartphone with 5G", 700.0, 10),
                    new Product("Headphones", "Wireless noise-cancelling headphones", 150.0, 15),
                    new Product("Smartwatch", "Fitness smartwatch", 200.0, 8)
            );

            for (Product product : sampleProducts) {
                // Check if a product with the same name exists
                if (productRepository.findByName(product.getName()) == null) {
                    productRepository.save(product);
                    System.out.println("✅ Added product: " + product.getName());
                } else {
                    System.out.println("Product already exists: " + product.getName());
                }
            }

            System.out.println("✅ Product initialization complete!");
        };
    }
}
