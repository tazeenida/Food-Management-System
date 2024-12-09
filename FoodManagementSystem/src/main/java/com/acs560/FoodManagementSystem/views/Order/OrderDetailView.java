package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

/**
 * The {@link OrderDetailView} class represents a form for displaying the details of an order.
 * It extends {@link FormLayout} and utilizes various text fields to present order data.
 * The form is designed to be read-only and displays order details from an {@link OrderEntity}.
 * It supports binding fields from the {@link OrderEntity} class to the UI components for display.
 */
public class OrderDetailView extends FormLayout {

    private static final long serialVersionUID = 476310807171214015L;

    private final TextField orderId = new TextField("Order ID");
    private final TextField costOfOrder = new TextField("Cost of Order");
    private final TextField dayOfTheWeek = new TextField("Day of the Week");
    private final TextField customerId = new TextField("Customer ID"); 
    private final TextField restaurantId = new TextField("Restaurant ID");

    private final Binder<OrderEntity> binder = new BeanValidationBinder<>(OrderEntity.class);

    /**
     * Constructs a new instance of {@link OrderDetailView}.
     * Initializes the form fields and binds them to the corresponding fields in {@link OrderEntity}.
     * All fields are set to read-only to prevent editing of the displayed order data.
     */
    public OrderDetailView() {
        addClassName("order-detail");

        // Bind the form fields to the corresponding properties in the OrderEntity class
        binder.bindInstanceFields(this);

        // Set the fields to read-only since the form is for displaying order details only
        orderId.setReadOnly(true);
        costOfOrder.setReadOnly(true);
        dayOfTheWeek.setReadOnly(true);
        customerId.setReadOnly(true);
        restaurantId.setReadOnly(true);

        // Add the fields to the form layout
        add(orderId, costOfOrder, dayOfTheWeek, customerId, restaurantId);  
        setWidth("25em"); // Set the width of the form layout
    }

    /**
     * Updates the form with order data for display purposes.
     * This method populates the form fields with the values from the provided {@link OrderEntity}.
     *
     * @param order the order to be displayed, represented as an {@link OrderEntity}
     */
    public void setOrder(OrderEntity order) {
        binder.readBean(order); // Bind the order data to the form fields for display
    }

    /**
     * Clears the form fields when no order is selected.
     * This method resets the binder and clears any displayed data in the form fields.
     */
    public void clear() {
        binder.readBean(null); // Clear the form data by unbinding the current bean
    }
}
