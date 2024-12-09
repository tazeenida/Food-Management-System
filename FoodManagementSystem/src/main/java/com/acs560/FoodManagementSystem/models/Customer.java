package com.acs560.FoodManagementSystem.models;

import lombok.*;

/**
 * Represents a customer in the food management system.
 * This class is used to store information about a customer, including their ID and rating.
 * It is part of the data model used for transferring customer-related data within the application.
 *
 * <p>The `Customer` class is typically used in scenarios such as order placement, customer management, 
 * and customer feedback processing.</p>
 */
@Data
@NoArgsConstructor
public class Customer {
    
    /**
     * Unique identifier for the customer.
     * This is a unique ID assigned to each customer in the system.
     */
    private Integer customerId;

    /**
     * Rating given to the customer.
     * This field represents the rating or feedback given to the customer, 
     * which could be used for customer loyalty points, feedback tracking, or other purposes.
     */
    private Float rating;
}
