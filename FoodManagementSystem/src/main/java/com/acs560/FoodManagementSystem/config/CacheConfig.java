package com.acs560.FoodManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for setting up caching in the Food Management System.
 * This class initializes a custom {@link CacheManager} bean that manages caching for various entities.
 * Caching helps improve performance by storing frequently accessed data in memory, reducing the need for 
 * repeated database queries or computations.
 *
 * <p>The {@link CacheManager} is configured to cache orders, customers, restaurants, and related queries 
 * based on certain filters such as cost, day, and customer rating range.</p>
 *
 * <p>Additionally, the {@link CacheConfig} class logs the initialization of the cache manager for debugging purposes.</p>
 */
@Configuration
public class CacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * Bean definition for {@link CacheManager}.
     * This method initializes the {@link CacheManager} and configures it with various cache names.
     *
     * <p>The cache manager is used to manage caches for different entities like orders, customers, and restaurants, 
     * allowing efficient retrieval of data by caching frequently requested data based on specific filters.</p>
     *
     * @return a {@link CacheManager} instance configured with multiple cache names
     */
    @Bean
    public CacheManager cacheManager() {
        logger.info("Initializing LoggingCacheManager Bean");

        return new LoggingCacheManager(
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
