package com.acs560.FoodManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The {@link FoodManagementSystem} class is the entry point for the Food Management System application.
 * It is annotated with {@link SpringBootApplication}, which indicates that it is a Spring Boot application.
 * This class is responsible for launching the Spring Boot application and enabling caching functionality in the system.
 * 
 * The {@link @SpringBootApplication} annotation enables component scanning, auto-configuration, and property support in the application.
 * The {@link @EnableCaching} annotation enables caching support, allowing the system to cache results for performance improvements.
 */
@SpringBootApplication(scanBasePackages = {"com.acs560.FoodManagementSystem", "com.acs560.FoodManagementSystem.config"})
@EnableCaching
public class FoodManagementSystem {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     * It initializes the Spring application context and starts the Food Management System application.
     * 
     * @param args command-line arguments (if any)
     */
    public static void main(String[] args) {
        SpringApplication.run(FoodManagementSystem.class, args);
    }
}
