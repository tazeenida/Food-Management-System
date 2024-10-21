package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

/**
 * The form to display order details.
 */
public class OrderDetailView extends FormLayout {

    private static final long serialVersionUID = 476310807171214015L;

    private final TextField orderId = new TextField("Order ID");
    private final TextField costOfOrder = new TextField("Cost of Order");
    private final TextField dayOfTheWeek = new TextField("Day of the Week");
    private final TextField customerId = new TextField("Customer ID"); // Assuming you want to display this
    private final TextField restaurantId = new TextField("Restaurant ID"); // Assuming you want to display this

    private final Binder<OrderEntity> binder = new BeanValidationBinder<>(OrderEntity.class);

    /**
     * Constructor
     */
    public OrderDetailView() {
        addClassName("order-detail");

        binder.bindInstanceFields(this); // Bind fields to OrderEntity fields

        // Make fields read-only for display purposes
        orderId.setReadOnly(true);
        costOfOrder.setReadOnly(true);
        dayOfTheWeek.setReadOnly(true);
        customerId.setReadOnly(true);
        restaurantId.setReadOnly(true);

        add(orderId, costOfOrder, dayOfTheWeek, customerId, restaurantId);  // Add the fields to the form layout
        setWidth("25em");
    }

    /**
     * Update the form with order data for display purposes.
     * @param order - the order to be displayed
     */
    public void setOrder(OrderEntity order) {
        binder.readBean(order); // Bind the order data to the form fields
    }

    /**
     * Clear form fields when no order is selected.
     */
    public void clear() {
        binder.readBean(null);  // Clear the form when no order is selected
    }
}