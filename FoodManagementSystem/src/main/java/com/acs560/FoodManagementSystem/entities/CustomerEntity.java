package com.acs560.FoodManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a customer entity in the food management system.
 * This class is mapped to the "Customers" table in the database and contains 
 * details about a customer, including their unique ID and rating.
 *
 * <p>The `CustomerEntity` class is used to store and retrieve customer information 
 * in the system, with fields for the customer's ID and rating.</p>
 */
@Entity
@Table(name = "Customers")
@Data
@NoArgsConstructor
public class CustomerEntity {

    /**
     * Unique identifier for the customer.
     * This field is automatically generated as the primary key for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    /**
     * Customer's rating based on feedback or performance.
     * This field cannot be null and is used to store the customer's rating.
     */
    @NotNull
    private float rating;
}
