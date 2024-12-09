package com.acs560.FoodManagementSystem.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object for creating or updating an order.
 * <p>
 * This class contains the necessary details to create or update an order in the food management system,
 * including the cost of the order, the day it was placed, and the IDs of the customer and restaurant
 * involved in the order. All fields are validated to ensure that required information is provided.
 * </p>
 */
@Data
@NoArgsConstructor
public class OrderRequest {

    /**
     * The cost of the order.
     * <p>
     * This value is required and cannot be null. It represents the total cost for the order.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotNull
     */
    @NotNull(message="Cost Of Order is Required")
    private Float costOfOrder;

    /**
     * The day of the week when the order is placed.
     * <p>
     * This value is required and cannot be empty. It specifies the day (e.g., "Monday", "Tuesday")
     * when the order was made.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotEmpty
     */
    @NotEmpty(message="Day Of The Week is Required")
    private String dayOfTheWeek;

    /**
     * The unique identifier of the customer placing the order.
     * <p>
     * This value is required and cannot be null. It represents the ID of the customer who
     * placed the order.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotNull
     */
    @NotNull(message="Customer Id is Required")
    private Integer customerId;

    /**
     * The unique identifier of the restaurant fulfilling the order.
     * <p>
     * This value is required and cannot be null. It represents the ID of the restaurant
     * that will fulfill the order.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotNull
     */
    @NotNull(message="Restaurant Id is Required")
    private Integer restaurantId;
}
