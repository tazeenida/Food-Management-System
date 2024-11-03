package com.acs560.FoodManagementSystem.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acs560.FoodManagementSystem.entities.CustomerEntity;
import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.entities.RestaurantEntity;
import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.repositories.CustomerRepository;
import com.acs560.FoodManagementSystem.repositories.OrderRepository;
import com.acs560.FoodManagementSystem.repositories.RestaurantRepository;
import com.acs560.FoodManagementSystem.services.OrderService;

import jakarta.transaction.Transactional;

/**
 * Implementation of the {@link OrderService} interface for managing order-related operations.
 * <p>
 * This class provides methods for retrieving order information based on various criteria,
 * such as order ID, cost, customer, and restaurant. It utilizes the {@link OrderRepository}
 * to access order data from the underlying data source.
 * </p>
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository; 
    @Autowired
    private CustomerRepository customerRepository; 

    /**
     * Constructs a new instance of {@link OrderServiceImpl}.
     *
     * @param orderRepository the repository used to access order data
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    
    /**
     * Retrieves a list of all orders.
     *
     * @return a list of all {@link OrderEntity} objects
     */
    @Override
    public List<OrderEntity> getAll() {
        List<OrderEntity> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(orderList::add);
        return orderList;
    }
    
    /**
     * Retrieves an order by its unique order ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return an {@link Optional} containing the {@link OrderEntity} if found,
     *         or an empty Optional if not found
     */
    @Override
    public Optional<OrderEntity> getByOrderId(Integer orderId) {
        return Optional.ofNullable(orderRepository.findByOrderId(orderId));
    }

    /**
     * Retrieves a list of orders with a specific cost.
     *
     * @param costOfOrder the cost of the order to filter
     * @return a list of {@link OrderEntity} objects matching the specified cost
     */
    @Override
    public List<OrderEntity> getByCostOfOrder(float costOfOrder) {
        return orderRepository.findByCostOfOrder(costOfOrder);
    }

    /**
     * Retrieves a list of orders made on a specific day of the week.
     *
     * @param dayOfTheWeek the day of the week to filter orders
     * @return a list of {@link OrderEntity} objects matching the specified day of the week
     */
    @Override
    public List<OrderEntity> getByDayOfTheWeek(String dayOfTheWeek) {
        return orderRepository.findByDayOfTheWeek(dayOfTheWeek);
    }

    /**
     * Retrieves a list of orders placed by a specific customer.
     *
     * @param customerId the ID of the customer whose orders to retrieve
     * @return a list of {@link OrderEntity} objects associated with the specified customer
     */
    @Override
    public List<OrderEntity> getByCustomer_CustomerId(Integer customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }

    /**
     * Retrieves a list of orders associated with a specific restaurant.
     *
     * @param restaurantId the ID of the restaurant whose orders to retrieve
     * @return a list of {@link OrderEntity} objects associated with the specified restaurant
     */
    @Override
    public List<OrderEntity> getByRestaurant_RestaurantId(Integer restaurantId) {
        return orderRepository.findByRestaurant_RestaurantId(restaurantId);
    }
    
    
    @Transactional
    public void addOrder(Order order, String restaurantName, Integer foodPreparationTime, Integer deliveryTime, float customerRating) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setRating(customerRating);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setRestaurantName(restaurantName);
        restaurantEntity.setFoodPreparationTime(foodPreparationTime);
        restaurantEntity.setDeliveryTime(deliveryTime);
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurantEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCostOfOrder(order.getCostOfOrder());
        orderEntity.setDayOfTheWeek(order.getDayOfTheWeek());
        orderEntity.setCustomer(savedCustomer);
        orderEntity.setRestaurant(savedRestaurant);

        orderRepository.save(orderEntity);
    }
    
    @Transactional
    public void updateOrder(Integer orderId, Order updatedOrder, String restaurantName, Integer foodPreparationTime, Integer deliveryTime, float customerRating) {
        // Fetch the existing order entity by orderId
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity == null) {
            throw new IllegalArgumentException("Order not found for ID: " + orderId);
        }

        // Update order fields from the updatedOrder object
        orderEntity.setCostOfOrder(updatedOrder.getCostOfOrder());
        orderEntity.setDayOfTheWeek(updatedOrder.getDayOfTheWeek());
        // Update any additional fields from updatedOrder as necessary

        // Update restaurant details
        RestaurantEntity restaurantEntity = orderEntity.getRestaurant();
        if (restaurantEntity != null) {
            restaurantEntity.setRestaurantName(restaurantName);
            restaurantEntity.setFoodPreparationTime(foodPreparationTime);
            restaurantEntity.setDeliveryTime(deliveryTime);
        }

        // Update customer rating
        CustomerEntity customerEntity = orderEntity.getCustomer();
        if (customerEntity != null) {
            customerEntity.setRating(customerRating);
        }

        // Save the updated entities
        orderRepository.save(orderEntity);
        if (restaurantEntity != null) {
            restaurantRepository.save(restaurantEntity);
        }
        if (customerEntity != null) {
            customerRepository.save(customerEntity);
        }
    }


    
}
