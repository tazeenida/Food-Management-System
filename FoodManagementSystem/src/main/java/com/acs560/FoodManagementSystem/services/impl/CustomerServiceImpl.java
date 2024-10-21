package com.acs560.FoodManagementSystem.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.acs560.FoodManagementSystem.entities.CustomerEntity;
import com.acs560.FoodManagementSystem.repositories.CustomerRepository;
import com.acs560.FoodManagementSystem.services.CustomerService;

/**
 * Implementation of the {@link CustomerService} interface for managing customer-related operations.
 * This class provides methods for retrieving customer information based on various criteria.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Constructs a new instance of {@link CustomerServiceImpl}.
     *
     * @param customerRepository the repository used to access customer data
     */
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves a customer by their unique customer ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return an {@link Optional} containing the {@link CustomerEntity} if found, or an empty Optional if not found
     */
    @Override
    public Optional<CustomerEntity> getByCustomerId(Integer customerId) {
        return Optional.ofNullable(customerRepository.findByCustomerId(customerId));
    }

    /**
     * Retrieves a list of customers with a specific rating.
     *
     * @param rating the rating to filter customers
     * @return a list of {@link CustomerEntity} objects matching the specified rating
     */
    @Override
    public List<CustomerEntity> getByRating(float rating) {
        return customerRepository.findByRating(rating);
    }
}
