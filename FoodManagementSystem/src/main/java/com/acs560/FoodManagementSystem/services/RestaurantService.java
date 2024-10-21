package com.acs560.FoodManagementSystem.services;

import java.util.List;
import java.util.Optional;

import com.acs560.FoodManagementSystem.entities.RestaurantEntity;

/**
 * Service interface for managing restaurant-related operations.
 * Provides methods for retrieving restaurant information based on various criteria.
 */
public interface RestaurantService {
	

    /**
     * Retrieves a restaurant by its unique restaurant ID.
     *
     * @param restaurantId the ID of the restaurant to retrieve
     * @return an {@link Optional} containing the {@link RestaurantEntity} if found, or an empty Optional if not found
     */
    Optional<RestaurantEntity> getByRestaurantId(Integer restaurantId);

    /**
     * Retrieves a list of restaurants with a specific name.
     *
     * @param restaurantName the name of the restaurant to find
     * @return a list of {@link RestaurantEntity} objects matching the specified name
     */
    List<RestaurantEntity> getByRestaurantName(String restaurantName);

    /**
     * Retrieves a list of restaurants that have a specific food preparation time.
     *
     * @param foodPreparationTime the food preparation time to filter restaurants
     * @return a list of {@link RestaurantEntity} objects matching the specified preparation time
     */
    List<RestaurantEntity> getByFoodPreparationTime(Integer foodPreparationTime);

    /**
     * Retrieves a list of restaurants that have a specific delivery time.
     *
     * @param deliveryTime the delivery time to filter restaurants
     * @return a list of {@link RestaurantEntity} objects matching the specified delivery time
     */
    List<RestaurantEntity> getByDeliveryTime(Integer deliveryTime);

	List<RestaurantEntity> getAll();
}
