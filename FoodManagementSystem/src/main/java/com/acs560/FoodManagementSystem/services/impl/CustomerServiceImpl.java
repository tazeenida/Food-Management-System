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
import com.acs560.FoodManagementSystem.repositories.OrderRepository;
import com.acs560.FoodManagementSystem.repositories.RestaurantRepository;
import com.acs560.FoodManagementSystem.repositories.CustomerRepository;
import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of the {@link CustomerService} interface for managing customer-related operations.
 * <p>
 * This class provides methods for retrieving customer information based on various criteria,
 * such as customer ID and rating. It utilizes the {@link CustomerRepository} to access
 * customer data from the underlying data source.
 * </p>
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Constructs a new instance of {@link CustomerServiceImpl}.
     *
     * @param customerRepository the repository used to access customer data
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves a list of all customers.
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
     *
     * @param customerId the ID of the customer to retrieve
     * @return an {@link Optional} containing the {@link CustomerEntity} if found,
     *         or an empty Optional if not found
     */
    @Override
    @Cacheable(value = "customers", key = "#customerId")
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
    @Cacheable(value = "customers", key = "#rating")
    public List<CustomerEntity> getByRating(float rating) {
        return customerRepository.findByRating(rating);
    }
}
