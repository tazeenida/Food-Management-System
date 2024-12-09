package com.acs560.FoodManagementSystem.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a restaurant in the food management system.
 * This class holds information about a restaurant such as its ID, name, 
 * average food preparation time, and delivery time.
 * It is part of the data model used for transferring restaurant-related data within the application.
 *
 * <p>The `Restaurant` class is used to track the basic details of restaurants in the system, 
 * such as their name, preparation time, and delivery time, which are crucial for managing 
 * orders and ensuring timely deliveries to customers.</p>
 */
@Data
@NoArgsConstructor
@ToString
public class Restaurant {
    
    /**
     * Unique identifier for the restaurant.
     * This field stores the ID assigned to the restaurant in the system.
     */
    private Integer restaurantId;

    /**
     * Name of the restaurant. This field cannot be null.
     * The name of the restaurant is a required field and is used to identify the restaurant.
     */
    @NotNull
    private String restaurantName;

    /**
     * Average time (in minutes) for food preparation at the restaurant.
     * This field represents the typical duration needed by the restaurant to prepare the food.
     */
    private Integer foodPreparationTime;

    /**
     * Average time (in minutes) for delivery from the restaurant.
     * This field indicates how long it generally takes for the restaurant to deliver the food to the customer.
     */
    private Integer deliveryTime;
}
