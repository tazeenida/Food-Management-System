package com.acs560.FoodManagementSystem.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object for creating or updating a restaurant.
 * <p>
 * This class contains the necessary details to create or update a restaurant in the food management system,
 * including the restaurant's name, food preparation time, and delivery time. All fields are validated
 * to ensure that required information is provided.
 * </p>
 */
@Data
@NoArgsConstructor
public class RestaurantRequest {

    /**
     * The name of the restaurant.
     * <p>
     * This value is required and cannot be empty. It represents the name of the restaurant.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotEmpty
     */
    @NotEmpty(message ="Restaurant Name is required")
    private String restaurantName;

    /**
     * The food preparation time in minutes.
     * <p>
     * This value is required and cannot be null. It specifies the time, in minutes, required by the restaurant
     * to prepare food.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotNull
     */
    @NotNull(message ="Food Preparation Time is required")
    private Integer foodPreparationTime;

    /**
     * The delivery time in minutes.
     * <p>
     * This value is required and cannot be null. It represents the time, in minutes, required for the restaurant
     * to deliver the food to the customer.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotNull
     */
    @NotNull(message ="Delivery Time is required")
    private Integer deliveryTime;
}
