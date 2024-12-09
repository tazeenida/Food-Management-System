package com.acs560.FoodManagementSystem.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.acs560.FoodManagementSystem.entities.CustomerEntity;

/**
 * Repository interface for accessing and manipulating {@link CustomerEntity} data.
 * <p>
 * This interface provides methods to perform CRUD operations on the customer data stored in the database. 
 * It extends {@link CrudRepository}, which offers basic operations such as saving, updating, and deleting customers.
 * </p>
 */
public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {

    /**
     * Find a customer by their unique customer ID.
     *
     * @param customerId the ID of the customer to find
     * @return the {@link CustomerEntity} with the specified ID, or null if not found
     */
    CustomerEntity findByCustomerId(Integer customerId);

    /**
     * Find all customers with a specific rating.
     *
     * @param rating the rating to filter customers
     * @return a list of {@link CustomerEntity} objects matching the specified rating
     */
    List<CustomerEntity> findByRating(float rating);
}
