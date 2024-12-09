package com.acs560.FoodManagementSystem.services;

import java.util.List;
import java.util.Optional;

import com.acs560.FoodManagementSystem.entities.RestaurantEntity;

/**
 * Service interface for managing restaurant-related operations.
 * <p>
 * This interface provides methods for retrieving and managing restaurant information
 * based on various criteria, such as restaurant ID, name, food preparation time,
 * and delivery time. It enables interaction with the restaurant data in the food 
 * delivery system.
 * </p>
 */
public interface RestaurantService {
    
    /**
     * Retrieves a list of all restaurants.
     * <p>
     * This method fetches all restaurant records from the data source and returns them
     * as a list of {@link RestaurantEntity} objects.
     * </p>
     *
     * @return a list of all {@link RestaurantEntity} objects
     */
    List<RestaurantEntity> getAll();

    /**
     * Retrieves a restaurant by its unique restaurant ID.
     * <p>
     * This method fetches a restaurant record from the data source using the
     * specified restaurant ID. If a restaurant with the given ID exists, it is returned
     * wrapped in an {@link Optional}. Otherwise, an empty {@link Optional} is returned.
     * </p>
     *
     * @param restaurantId the ID of the restaurant to retrieve
     * @return an {@link Optional} containing the {@link RestaurantEntity} if found, or
     *         an empty {@link Optional} if not found
     */
    Optional<RestaurantEntity> getByRestaurantId(Integer restaurantId);

    /**
     * Retrieves a list of restaurants with a specific name.
     * <p>
     * This method queries the data source to find all restaurants matching the specified
     * name. It returns a list of {@link RestaurantEntity} objects with the given name.
     * </p>
     *
     * @param restaurantName the name of the restaurant to find
     * @return a list of {@link RestaurantEntity} objects matching the specified name
     */
    List<RestaurantEntity> getByRestaurantName(String restaurantName);

    /**
     * Retrieves a list of restaurants that have a specific food preparation time.
     * <p>
     * This method filters restaurants based on the time required for food preparation.
     * It returns a list of {@link RestaurantEntity} objects with the specified preparation time.
     * </p>
     *
     * @param foodPreparationTime the food preparation time to filter restaurants
     * @return a list of {@link RestaurantEntity} objects matching the specified preparation time
     */
    List<RestaurantEntity> getByFoodPreparationTime(Integer foodPreparationTime);

    /**
     * Retrieves a list of restaurants that have a specific delivery time.
     * <p>
     * This method filters restaurants based on the time required for delivery.
     * It returns a list of {@link RestaurantEntity} objects with the specified delivery time.
     * </p>
     *
     * @param deliveryTime the delivery time to filter restaurants
     * @return a list of {@link RestaurantEntity} objects matching the specified delivery time
     */
    List<RestaurantEntity> getByDeliveryTime(Integer deliveryTime);
}
