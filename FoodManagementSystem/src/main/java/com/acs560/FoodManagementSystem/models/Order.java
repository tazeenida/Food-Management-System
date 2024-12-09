package com.acs560.FoodManagementSystem.models;

import com.acs560.FoodManagementSystem.entities.CustomerEntity;
import com.acs560.FoodManagementSystem.entities.RestaurantEntity;
import lombok.*;

/**
 * Represents an order in the food management system.
 * This class is used to store order-related data, such as the order ID, total cost, 
 * the day the order was placed, the customer who placed it, and the restaurant from which it was made.
 * It is part of the data model used for transferring order-related data within the application.
 *
 * <p>The `Order` class is typically used to track customer orders, calculate total costs, 
 * and manage relationships between customers and restaurants in the system.</p>
 */
@Data
@NoArgsConstructor
public class Order {
    
    /**
     * Unique identifier for the order.
     * This is a unique ID assigned to each order in the system.
     */
    private Integer orderId;

    /**
     * Total cost of the order.
     * This field represents the total cost of all items in the order.
     */
    private float costOfOrder;

    /**
     * Day of the week when the order was placed.
     * This is a string representing the day (e.g., "Monday", "Tuesday") when the order was made.
     */
    private String dayOfTheWeek;

    /**
     * Customer who placed the order.
     * This field refers to the `CustomerEntity` representing the customer who made the order.
     */
    private CustomerEntity customerId;

    /**
     * Restaurant from which the order was made.
     * This field refers to the `RestaurantEntity` representing the restaurant where the order originated.
     */
    private RestaurantEntity restaurantId;
}
