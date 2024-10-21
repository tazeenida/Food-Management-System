package com.acs560.FoodManagementSystem.services;

import java.util.List;
import java.util.Optional;

import com.acs560.FoodManagementSystem.entities.OrderEntity;

/**
 * Service interface for managing order-related operations.
 * <p>
 * This interface provides methods for retrieving order information
 * based on various criteria, such as order ID, cost, customer ID, 
 * and restaurant ID.
 * </p>
 */
public interface OrderService {

    /**
     * Retrieves a list of all orders.
     *
     * @return a list of all {@link OrderEntity} objects
     */
    List<OrderEntity> getAll();
    
    /**
     * Retrieves an order by its unique order ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return an {@link Optional} containing the {@link OrderEntity} if found,
     *         or an empty Optional if not found
     */
    Optional<OrderEntity> getByOrderId(Integer orderId);

    /**
     * Retrieves a list of orders with a specific cost.
     *
     * @param costOfOrder the cost of the orders to find
     * @return a list of {@link OrderEntity} objects matching the specified cost
     */
    List<OrderEntity> getByCostOfOrder(float costOfOrder);

    /**
     * Retrieves a list of orders placed on a specific day of the week.
     *
     * @param dayOfTheWeek the day of the week to filter orders
     * @return a list of {@link OrderEntity} objects placed on the specified day
     */
    List<OrderEntity> getByDayOfTheWeek(String dayOfTheWeek);

    /**
     * Retrieves a list of orders associated with a specific customer ID.
     *
     * @param customerId the ID of the customer whose orders are to be found
     * @return a list of {@link OrderEntity} objects associated with the specified customer
     */
    List<OrderEntity> getByCustomer_CustomerId(Integer customerId);

    /**
     * Retrieves a list of orders associated with a specific restaurant ID.
     *
     * @param restaurantId the ID of the restaurant whose orders are to be found
     * @return a list of {@link OrderEntity} objects associated with the specified restaurant
     */
    List<OrderEntity> getByRestaurant_RestaurantId(Integer restaurantId);
    
}
