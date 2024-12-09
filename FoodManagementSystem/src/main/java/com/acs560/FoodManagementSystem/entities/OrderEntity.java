package com.acs560.FoodManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents an order entity in the food management system.
 * This class is mapped to the "Orders" table in the database and contains 
 * details about an order, including its ID, cost, day of the week, customer, 
 * and restaurant associated with the order.
 *
 * <p>The `OrderEntity` class is used to store and retrieve information about 
 * an order placed by a customer at a restaurant.</p>
 */
@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
public class OrderEntity {

    /**
     * Unique identifier for the order.
     * This field is automatically generated as the primary key for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    /**
     * Total cost of the order.
     * This field represents the monetary amount that the customer paid for the order.
     */
    private float costOfOrder;

    /**
     * Day of the week when the order was placed.
     * This field stores the day (e.g., Monday, Tuesday) when the order was placed.
     */
    private String dayOfTheWeek;

    /**
     * Customer who placed the order.
     * This field cannot be null and represents the customer who made the order.
     * The relationship is mapped to the `CustomerEntity` class.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    /**
     * Restaurant from which the order was placed.
     * This field cannot be null and represents the restaurant where the order was made.
     * The relationship is mapped to the `RestaurantEntity` class.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
