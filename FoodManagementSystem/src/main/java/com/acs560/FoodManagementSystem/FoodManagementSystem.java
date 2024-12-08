package com.acs560.FoodManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.acs560.FoodManagementSystem", "com.acs560.FoodManagementSystem.config"})
@EnableCaching
public class FoodManagementSystem {

    public static void main(String[] args) {
        SpringApplication.run(FoodManagementSystem.class, args);
    }
}
