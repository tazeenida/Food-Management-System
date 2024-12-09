package com.acs560.FoodManagementSystem.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object for creating or updating customer information.
 * <p>
 * This class contains the customer's rating, which is a required field for both creating and updating
 * customer details in the system. The rating is validated to ensure that it is not null.
 * </p>
 */
@Data
@NoArgsConstructor
public class CustomerRequest {

    /**
     * The rating of the customer.
     * <p>
     * This value is required and cannot be null. It represents feedback or performance rating given
     * to the customer, and is essential for processing customer-related requests.
     * </p>
     * 
     * @see jakarta.validation.constraints.NotNull
     */
    @NotNull(message = "Rating is Required")
    private Float rating;
}
