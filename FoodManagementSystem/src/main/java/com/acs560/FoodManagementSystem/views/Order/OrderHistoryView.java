package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.router.PageTitle;
import com.acs560.FoodManagementSystem.views.MainLayout;

import java.util.List;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * View for displaying the order history of a customer.
 */
@PermitAll
@Route(value = "order-history", layout = MainLayout.class)
@PageTitle("Order History | Food Management System")
public class OrderHistoryView extends VerticalLayout {

    private OrderService orderService;
    private Grid<OrderEntity> orderGrid;
    private IntegerField customerIdField;
    private Button fetchButton;

    /**
     * Constructs the OrderHistoryView with the provided order service.
     *
     * @param orderService The service used to fetch order data.
     */
    public OrderHistoryView(OrderService orderService) {
        this.orderService = orderService;

        // Set up the layout components
        add(createHeader(), createInputLayout(), createOrderGrid());

        // Basic UI configuration
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER);
    }

    private Div createHeader() {
        H2 header = new H2("Customer Order History");
        Div headerLayout = new Div(header);
        headerLayout.setWidthFull();
        return headerLayout;
    }

    private Div createInputLayout() {
        customerIdField = new IntegerField("Customer ID");
        customerIdField.setPlaceholder("Enter Customer ID");
        customerIdField.setWidth("300px");

        fetchButton = new Button("Get Order History", event -> fetchOrderHistory());

        Div inputLayout = new Div(customerIdField, fetchButton);
        inputLayout.getStyle().set("display", "flex");
        inputLayout.getStyle().set("gap", "10px");
        return inputLayout;
    }

    private Grid<OrderEntity> createOrderGrid() {
        orderGrid = new Grid<>(OrderEntity.class, false);
        configureOrderGrid();
        orderGrid.setWidthFull();
        orderGrid.setHeight("400px");
        return orderGrid;
    }

    private void configureOrderGrid() {
        orderGrid.addColumn(order -> order.getCustomer().getCustomerId())
                .setHeader("Customer ID");
        orderGrid.addColumn(order -> order.getRestaurant().getRestaurantName())
                .setHeader("Restaurant Name");
        orderGrid.addColumn(order -> order.getCustomer().getRating())
                .setHeader("Customer Rating");
    }

    /**
     * Fetches the order history for the given customer ID and populates the grid.
     */
    private void fetchOrderHistory() {
        Integer customerId = customerIdField.getValue();
        if (customerId != null && customerId > 0) {
            List<OrderEntity> orders = orderService.getOrderHistoryByCustomerId(customerId);
            if (orders != null && !orders.isEmpty()) {
                orderGrid.setItems(orders);
            } else {
                showNotification("No orders found for the given Customer ID.");
                orderGrid.setItems();  // Clear grid data
            }
        } else {
            showNotification("Please enter a valid Customer ID.");
            orderGrid.setItems();  // Clear grid data
        }
    }

    /**
     * Displays a notification with the given message.
     * @param message the notification message
     */
    private void showNotification(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}
