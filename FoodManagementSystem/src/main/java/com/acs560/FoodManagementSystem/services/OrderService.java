package com.acs560.FoodManagementSystem.services;

import java.util.List;
import java.util.Optional;

import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.models.Order;

/**
 * Service interface for managing order-related operations.
 * <p>
 * This interface provides methods for retrieving and managing order information
 * based on various criteria, such as order ID, cost, customer ID, restaurant ID,
 * and customer rating. It abstracts the logic for interacting with the underlying
 * order data, enabling order management in a food delivery system.
 * </p>
 */
public interface OrderService {

    /**
     * Retrieves a list of all orders.
     * <p>
     * This method fetches all order records from the data source and returns them
     * as a list of {@link OrderEntity} objects.
     * </p>
     *
     * @return a list of all {@link OrderEntity} objects
     */
    List<OrderEntity> getAll();

    /**
     * Retrieves an order by its unique order ID.
     * <p>
     * This method fetches an order record from the data source using the
     * specified order ID. If an order with the given ID exists, it is returned
     * wrapped in an {@link Optional}. Otherwise, an empty {@link Optional} is returned.
     * </p>
     *
     * @param orderId the ID of the order to retrieve
     * @return an {@link Optional} containing the {@link OrderEntity} if found, or
     *         an empty {@link Optional} if not found
     */
    Optional<OrderEntity> getByOrderId(Integer orderId);

    /**
     * Retrieves a list of orders with a specific cost.
     * <p>
     * This method queries the data source to find all orders matching the specified
     * cost. It returns a list of {@link OrderEntity} objects with the given cost.
     * </p>
     *
     * @param costOfOrder the cost of the orders to find
     * @return a list of {@link OrderEntity} objects matching the specified cost
     */
    List<OrderEntity> getByCostOfOrder(float costOfOrder);

    /**
     * Retrieves a list of orders placed on a specific day of the week.
     * <p>
     * This method filters orders based on the day of the week they were placed. It
     * returns all orders placed on the specified day (e.g., "Monday", "Tuesday").
     * </p>
     *
     * @param dayOfTheWeek the day of the week to filter orders (e.g., "Monday", "Tuesday")
     * @return a list of {@link OrderEntity} objects placed on the specified day
     */
    List<OrderEntity> getByDayOfTheWeek(String dayOfTheWeek);

    /**
     * Retrieves a list of orders associated with a specific customer ID.
     * <p>
     * This method fetches all orders placed by a customer with the specified ID.
     * It returns a list of {@link OrderEntity} objects associated with that customer.
     * </p>
     *
     * @param customerId the ID of the customer whose orders are to be found
     * @return a list of {@link OrderEntity} objects associated with the specified customer
     */
    List<OrderEntity> getByCustomer_CustomerId(Integer customerId);

    /**
     * Retrieves a list of orders associated with a specific restaurant ID.
     * <p>
     * This method fetches all orders placed at the restaurant with the specified ID.
     * It returns a list of {@link OrderEntity} objects associated with that restaurant.
     * </p>
     *
     * @param restaurantId the ID of the restaurant whose orders are to be found
     * @return a list of {@link OrderEntity} objects associated with the specified restaurant
     */
    List<OrderEntity> getByRestaurant_RestaurantId(Integer restaurantId);

    /**
     * Adds a new order to the system.
     * <p>
     * This method saves a new order, along with the associated restaurant details,
     * food preparation time, delivery time, and customer rating.
     * </p>
     *
     * @param order               the {@link Order} object containing order details
     * @param restaurantName      the name of the restaurant associated with the order
     * @param foodPreparationTime the time required for food preparation
     * @param deliveryTime        the time required for delivery
     * @param customerRating      the customer's rating for the order
     */
    void addOrder(Order order, String restaurantName, Integer foodPreparationTime, Integer deliveryTime,
                  float customerRating);

    /**
     * Updates an existing order with new information.
     * <p>
     * This method modifies the details of an existing order, including the restaurant
     * name, food preparation time, delivery time, and customer rating.
     * </p>
     *
     * @param orderId             the ID of the order to update
     * @param updatedOrder        the {@link Order} object containing updated order details
     * @param restaurantName      the updated name of the restaurant associated with the order
     * @param foodPreparationTime the updated time required for food preparation
     * @param deliveryTime        the updated time required for delivery
     * @param customerRating      the updated customer's rating for the order
     */
    void updateOrder(Integer orderId, Order updatedOrder, String restaurantName, Integer foodPreparationTime,
                     Integer deliveryTime, Float customerRating);

    /**
     * Deletes an order from the system by its ID.
     * <p>
     * This method removes the order with the specified ID from the system.
     * </p>
     *
     * @param orderId the ID of the order to delete
     */
    void delete(Integer orderId);

    /**
     * Retrieves a list of orders where the customer rating falls within a specified range.
     * <p>
     * This method filters orders based on the customer rating, returning only those
     * whose ratings fall within the specified range.
     * </p>
     *
     * @param minRating the minimum rating in the range
     * @param maxRating the maximum rating in the range
     * @return a list of {@link OrderEntity} objects with a customer rating between the specified minimum and maximum values
     */
    List<OrderEntity> getByCustomerRatingRange(float minRating, float maxRating);

    /**
     * Retrieves a list of orders for a specific customer based on their customer ID.
     * <p>
     * This method fetches the order history for the customer identified by the given
     * customer ID. The order records are returned as a list of {@link OrderEntity} objects.
     * </p>
     *
     * @param customerId The unique ID of the customer whose order history is to be retrieved.
     * @return A list of {@link OrderEntity} objects representing the customer's order history.
     *         Returns an empty list if no orders are found for the specified customer.
     */
    List<OrderEntity> getOrderHistoryByCustomerId(Integer customerId);
}
