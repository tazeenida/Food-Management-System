package com.acs560.FoodManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a restaurant entity in the food management system.
 * This class is mapped to the "Restaurants" table in the database and contains 
 * details about a restaurant, including its ID, name, food preparation time, 
 * and delivery time.
 *
 * <p>The `RestaurantEntity` class is used to store and retrieve information about 
 * a restaurant, including its name and times related to food preparation and delivery.</p>
 */
@Entity
@Table(name = "Restaurants")
@Data
@NoArgsConstructor
public class RestaurantEntity {

    /**
     * Unique identifier for the restaurant.
     * This field is automatically generated as the primary key for the restaurant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    /**
     * Name of the restaurant.
     * This field represents the name of the restaurant and cannot be null.
     * It is a required field for creating or updating a restaurant entity.
     */
    @NotNull
    private String restaurantName;

    /**
     * Time taken to prepare food at the restaurant, in minutes.
     * This field represents the average time (in minutes) it takes for the restaurant 
     * to prepare the food once the order is placed.
     */
    private Integer foodPreparationTime;

    /**
     * Time taken for delivery from the restaurant, in minutes.
     * This field represents the average time (in minutes) it takes for the restaurant 
     * to deliver food to the customer after preparation.
     */
    private Integer deliveryTime;
}
