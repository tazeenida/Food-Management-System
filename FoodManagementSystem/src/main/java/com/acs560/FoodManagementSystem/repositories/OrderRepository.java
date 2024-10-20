package com.acs560.FoodManagementSystem.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acs560.FoodManagementSystem.entities.OrderEntity;

/**
 * Repository interface for accessing and manipulating OrderEntity data.
 */
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

    /**
     * Find an order by its unique order ID.
     *
     * @param orderId the ID of the order to find
     * @return the OrderEntity with the specified ID, or null if not found
     */
    OrderEntity findByOrderId(Integer orderId);

    /**
     * Find all orders with a specific cost.
     *
     * @param costOfOrder the cost of the orders to find
     * @return a list of OrderEntity objects matching the specified cost
     */
    List<OrderEntity> findByCostOfOrder(float costOfOrder);

    /**
     * Find all orders placed on a specific day of the week.
     *
     * @param dayOfTheWeek the day of the week to filter orders
     * @return a list of OrderEntity objects placed on the specified day
     */
    List<OrderEntity> findByDayOfTheWeek(String dayOfTheWeek);

    /**
     * Find all orders associated with a specific customer ID.
     *
     * @param customerId the ID of the customer whose orders are to be found
     * @return a list of OrderEntity objects associated with the specified customer
     */
    List<OrderEntity> findByCustomer_CustomerId(Integer customerId);

    /**
     * Find all orders associated with a specific restaurant ID.
     *
     * @param restaurantId the ID of the restaurant whose orders are to be found
     * @return a list of OrderEntity objects associated with the specified restaurant
     */
    List<OrderEntity> findByRestaurant_RestaurantId(Integer restaurantId);
}
