package com.acs560.FoodManagementSystem.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.acs560.FoodManagementSystem.entities.CustomerEntity;
import com.acs560.FoodManagementSystem.repositories.CustomerRepository;
import com.acs560.FoodManagementSystem.services.CustomerService;

/**
 * Implementation of the {@link CustomerService} interface for managing customer-related operations.
 * <p>
 * This service class provides methods for retrieving customer information from the repository based on various criteria.
 * The operations include fetching all customers, retrieving customers by their unique customer ID, and finding customers by rating.
 * The {@link CustomerRepository} is used for interacting with the underlying data store.
 * </p>
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Constructs a new instance of {@link CustomerServiceImpl}.
     * <p>
     * The constructor injects the {@link CustomerRepository} to facilitate interaction with the database
     * for accessing customer data.
     * </p>
     *
     * @param customerRepository the repository used to access customer data
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves a list of all customers.
     * <p>
     * This method fetches all customers from the repository and returns them as a list of {@link CustomerEntity} objects.
     * The list is cached to optimize repeated access to customer data.
     * </p>
     * 
     * @return a list of all {@link CustomerEntity} objects
     */
    @Override
    @Cacheable(value = "customers", key = "'all'")
    public List<CustomerEntity> getAll() {
        List<CustomerEntity> customerList = new ArrayList<>();
        customerRepository.findAll().forEach(customerList::add);
        return customerList;
    }

    /**
     * Retrieves a customer by their unique customer ID.
     * <p>
     * This method fetches a single customer based on the provided customer ID. If no customer is found,
     * an empty {@link Optional} is returned. The result is cached to improve performance on subsequent requests.
     * </p>
     *
     * @param customerId the ID of the customer to retrieve
     * @return an {@link Optional} containing the {@link CustomerEntity} if found,
     *         or an empty {@link Optional} if not found
     */
    @Override
    @Cacheable(value = "customers", key = "#customerId")
    public Optional<CustomerEntity> getByCustomerId(Integer customerId) {
        return Optional.ofNullable(customerRepository.findByCustomerId(customerId));
    }

    /**
     * Retrieves a list of customers with a specific rating.
     * <p>
     * This method retrieves customers whose rating matches the specified value. The list of customers is cached
     * for future use, reducing the need for repeated database queries.
     * </p>
     *
     * @param rating the rating to filter customers
     * @return a list of {@link CustomerEntity} objects matching the specified rating
     */
    @Override
    @Cacheable(value = "customers", key = "#rating")
    public List<CustomerEntity> getByRating(float rating) {
        return customerRepository.findByRating(rating);
    }
}
