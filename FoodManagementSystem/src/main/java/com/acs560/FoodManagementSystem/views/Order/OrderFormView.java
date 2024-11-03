package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@PermitAll
@Route(value = "order-form")
@PageTitle("Order Form | Food Management System")
public class OrderFormView extends VerticalLayout {

    private final OrderService orderService;

    private TextField orderIdField;
    private TextField costOfOrderField;
    private TextField dayOfTheWeekField;
    private TextField restaurantNameField;
    private IntegerField foodPreparationTimeField;
    private IntegerField deliveryTimeField;
    private TextField customerRatingField;
    private Button addOrderButton;
    private Button updateOrderButton;

    @Autowired
    public OrderFormView(OrderService orderService) {
        this.orderService = orderService;

        // Create UI components
        orderIdField = new TextField("Order ID (for update)");
        costOfOrderField = new TextField("Cost of Order");
        dayOfTheWeekField = new TextField("Day of the Week");
        restaurantNameField = new TextField("Restaurant Name");
        foodPreparationTimeField = new IntegerField("Food Preparation Time (minutes)");
        deliveryTimeField = new IntegerField("Delivery Time (minutes)");
        customerRatingField = new TextField("Customer Rating");

        addOrderButton = new Button("Add Order", event -> addOrder());
        updateOrderButton = new Button("Update Order", event -> updateOrder());
        Button backButton = new Button("Back to Orders", e -> getUI().ifPresent(ui -> ui.navigate(OrderListView.class)));

        FormLayout formLayout = new FormLayout();
        formLayout.add(orderIdField, costOfOrderField, dayOfTheWeekField, restaurantNameField,
                       foodPreparationTimeField, deliveryTimeField, customerRatingField,
                       addOrderButton, updateOrderButton, backButton);

        add(formLayout);
    }

    private void addOrder() {
        try {
            Order order = new Order();
            order.setCostOfOrder(Float.parseFloat(costOfOrderField.getValue()));
            order.setDayOfTheWeek(dayOfTheWeekField.getValue());

            String restaurantName = restaurantNameField.getValue();
            Integer foodPreparationTime = foodPreparationTimeField.getValue();
            Integer deliveryTime = deliveryTimeField.getValue();
            float customerRating = Float.parseFloat(customerRatingField.getValue());

            orderService.addOrder(order, restaurantName, foodPreparationTime, deliveryTime, customerRating);
            Notification.show("Order added successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            Notification.show("Please enter valid numeric values for Cost of Order and Customer Rating.");
        } catch (Exception e) {
            Notification.show("Error adding order: " + e.getMessage());
        }
    }

    private void updateOrder() {
        try {
            Integer orderId = Integer.parseInt(orderIdField.getValue());
            Order updatedOrder = new Order();
            updatedOrder.setCostOfOrder(Float.parseFloat(costOfOrderField.getValue()));
            updatedOrder.setDayOfTheWeek(dayOfTheWeekField.getValue());

            String restaurantName = restaurantNameField.getValue();
            Integer foodPreparationTime = foodPreparationTimeField.getValue();
            Integer deliveryTime = deliveryTimeField.getValue();
            float customerRating = Float.parseFloat(customerRatingField.getValue());

            orderService.updateOrder(orderId, updatedOrder, restaurantName, foodPreparationTime, deliveryTime, customerRating);
            Notification.show("Order updated successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            Notification.show("Please enter valid numeric values for Cost of Order, Customer Rating, and Order ID.");
        } catch (IllegalArgumentException e) {
            Notification.show("Error: " + e.getMessage());
        } catch (Exception e) {
            Notification.show("Error updating order: " + e.getMessage());
        }
    }

    private void clearFields() {
        orderIdField.clear();
        costOfOrderField.clear();
        dayOfTheWeekField.clear();
        restaurantNameField.clear();
        foodPreparationTimeField.clear();
        deliveryTimeField.clear();
        customerRatingField.clear();
    }
}
