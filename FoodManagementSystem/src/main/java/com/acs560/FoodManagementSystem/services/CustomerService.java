package com.acs560.FoodManagementSystem.services;

import java.util.List;
import java.util.Optional;

import com.acs560.FoodManagementSystem.entities.CustomerEntity;

/**
 * Service interface for managing customer-related operations.
 * <p>
 * This interface provides methods for retrieving customer information
 * based on various criteria, such as customer ID and rating. It abstracts
 * the logic for accessing customer data and provides the necessary contract
 * for implementing customer management services.
 * </p>
 */
public interface CustomerService {
    
    /**
     * Retrieves a list of all customers.
     * <p>
     * This method fetches all customer records from the data source and returns
     * them as a list of {@link CustomerEntity} objects.
     * </p>
     *
     * @return a list of all {@link CustomerEntity} objects
     */
    List<CustomerEntity> getAll();

    /**
     * Retrieves a customer by their unique customer ID.
     * <p>
     * This method fetches a customer record from the data source using the
     * specified customer ID. If a customer with the given ID exists, it is
     * returned wrapped in an {@link Optional}. Otherwise, an empty {@link Optional}
     * is returned.
     * </p>
     *
     * @param customerId the ID of the customer to retrieve
     * @return an {@link Optional} containing the {@link CustomerEntity} if found,
     *         or an empty {@link Optional} if not found
     */
    Optional<CustomerEntity> getByCustomerId(Integer customerId);

    /**
     * Retrieves a list of customers with a specific rating.
     * <p>
     * This method queries the data source to find all customers whose rating matches
     * the specified value. The rating can be used to filter customers based on their
     * feedback or satisfaction score.
     * </p>
     *
     * @param rating the rating to filter customers
     * @return a list of {@link CustomerEntity} objects matching the specified rating
     */
    List<CustomerEntity> getByRating(float rating);
    
}
