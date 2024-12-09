package com.acs560.FoodManagementSystem.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.acs560.FoodManagementSystem.entities.RestaurantEntity;

/**
 * Repository interface for accessing and manipulating {@link RestaurantEntity} data.
 * <p>
 * This interface extends {@link CrudRepository} to provide basic CRUD operations for managing restaurant data.
 * It includes custom query methods for retrieving restaurants based on specific attributes such as restaurant ID,
 * restaurant name, food preparation time, and delivery time.
 * </p>
 */
public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Integer> {

    /**
     * Find a restaurant by its unique restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to find
     * @return the {@link RestaurantEntity} with the specified ID, or null if not found
     */
    RestaurantEntity findByRestaurantId(Integer restaurantId);

    /**
     * Find all restaurants with a name containing the specified string, case-insensitive.
     *
     * @param restaurantName the name of the restaurant to find (part of the name)
     * @return a list of {@link RestaurantEntity} objects matching the specified name
     *         (case-insensitive search)
     */
    List<RestaurantEntity> findByRestaurantNameContainingIgnoreCase(String restaurantName);

    /**
     * Find all restaurants with a specific food preparation time.
     *
     * @param foodPreparationTime the food preparation time to filter restaurants
     * @return a list of {@link RestaurantEntity} objects matching the specified food preparation time
     */
    List<RestaurantEntity> findByFoodPreparationTime(Integer foodPreparationTime);

    /**
     * Find all restaurants with a specific delivery time.
     *
     * @param deliveryTime the delivery time to filter restaurants
     * @return a list of {@link RestaurantEntity} objects matching the specified delivery time
     */
    List<RestaurantEntity> findByDeliveryTime(Integer deliveryTime);
}
