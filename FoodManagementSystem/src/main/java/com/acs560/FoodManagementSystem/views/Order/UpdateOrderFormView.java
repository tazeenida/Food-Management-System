package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * The {@link UpdateOrderFormView} class provides a user interface for updating existing orders.
 */
@Route("update-order-form/:orderId")
@AnonymousAllowed
public class UpdateOrderFormView extends VerticalLayout {

    private final OrderService orderService;

    private final TextField orderIdField = new TextField("Order ID");
    private final TextField costOfOrderField = new TextField("Cost of Order");
    private final TextField dayOfWeekField = new TextField("Day of the Week");
    private final TextField restaurantNameField = new TextField("Restaurant Name");
    private final TextField foodPreparationTimeField = new TextField("Food Preparation Time (min)");
    private final TextField deliveryTimeField = new TextField("Delivery Time (min)");
    private final TextField customerRatingField = new TextField("Customer Rating");

    private final Button updateButton = new Button("Update Order");

    /**
     * Constructs a new instance of {@link UpdateOrderFormView}.
     *
     * @param orderService the service to manage order data
     */
    @Autowired
    public UpdateOrderFormView(OrderService orderService) {
        this.orderService = orderService;

        addClassName("update-order-form-view");
        add(createFormLayout());
        setSizeFull();

        // Get orderId from the route and load the order details
        String orderId = getOrderIdFromRoute();
        loadOrderDetails(orderId);

        updateButton.addClickListener(event -> updateOrder());
    }

    /**
     * Creates a form layout for input fields.
     *
     * @return a {@link FormLayout} containing input fields for order details
     */
    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(orderIdField, costOfOrderField, dayOfWeekField, restaurantNameField,
                foodPreparationTimeField, deliveryTimeField, customerRatingField, updateButton);
        return formLayout;
    }

    /**
     * Loads order details into the form based on the provided order ID.
     *
     * @param orderId the ID of the order to be updated
     */
    private void loadOrderDetails(String orderId) {
        Optional<OrderEntity> orderOpt = orderService.getByOrderId(Integer.parseInt(orderId));
        if (orderOpt.isPresent()) {
            OrderEntity order = orderOpt.get();
            orderIdField.setValue(order.getOrderId().toString());
            costOfOrderField.setValue(String.valueOf(order.getCostOfOrder()));
            dayOfWeekField.setValue(order.getDayOfTheWeek());
            restaurantNameField.setValue(order.getRestaurant().getRestaurantName());
            foodPreparationTimeField.setValue(String.valueOf(order.getRestaurant().getFoodPreparationTime()));
            deliveryTimeField.setValue(String.valueOf(order.getRestaurant().getDeliveryTime()));
            customerRatingField.setValue(String.valueOf(order.getCustomer().getRating()));
        } else {
            Notification.show("Order not found!");
        }
    }

    /**
     * Updates the existing order with the values from the form fields.
     */
    private void updateOrder() {
        try {
            Order order = new Order();
            order.setOrderId(Integer.parseInt(orderIdField.getValue()));
            order.setCostOfOrder(Float.parseFloat(costOfOrderField.getValue()));
            order.setDayOfTheWeek(dayOfWeekField.getValue());

            // Get order ID from the route as an Integer
            Integer orderId = Integer.parseInt(getOrderIdFromRoute());

            // Pass the updated values to the service method correctly
            orderService.updateOrder(orderId, order, restaurantNameField.getValue(),
                    Integer.parseInt(foodPreparationTimeField.getValue()),
                    Integer.parseInt(deliveryTimeField.getValue()),
                    Float.parseFloat(customerRatingField.getValue()));

            Notification.show("Order updated successfully!");
            clearFields(); // Optionally clear fields after submission
        } catch (NumberFormatException e) {
            Notification.show("Please enter valid numeric values for Cost, Preparation Time, Delivery Time, and Rating.");
        } catch (Exception e) {
            Notification.show("Failed to update order: " + e.getMessage());
        }
    }

    /**
     * Clears the input fields after order submission.
     */
    private void clearFields() {
        orderIdField.clear();
        costOfOrderField.clear();
        dayOfWeekField.clear();
        restaurantNameField.clear();
        foodPreparationTimeField.clear();
        deliveryTimeField.clear();
        customerRatingField.clear();
    }

    /**
     * Retrieves the order ID from the route.
     *
     * @return the order ID as a string
     */
    private String getOrderIdFromRoute() {
        String orderId = getElement().getAttribute("orderId");
        return orderId != null ? orderId : "0"; // default to "0" if not found
    }
}
