package com.acs560.FoodManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class CacheConfig {
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheManager cacheManager() {
        logger.info("Initializing CacheManager Bean");
        return new ConcurrentMapCacheManager(
                "orders", 
                "ordersByCost", 
                "ordersByDay", 
                "ordersByCustomer", 
                "ordersByRestaurant", 
                "ordersByCustomerRatingRange",
                "restaurants",
                "customers"
            );
    }
}
