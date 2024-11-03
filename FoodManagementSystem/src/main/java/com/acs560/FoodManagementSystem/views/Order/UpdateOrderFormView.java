package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
@Route(value = "update-order-form")
@PageTitle("Update Order Form | Food Management System")
public class UpdateOrderFormView extends VerticalLayout {

    private final OrderService orderService;

    private TextField orderIdField;
    private TextField costOfOrderField;
    private ComboBox<String> dayOfTheWeekField;
    private TextField restaurantNameField;
    private IntegerField foodPreparationTimeField;
    private IntegerField deliveryTimeField;
    private TextField customerRatingField;
    private Button addOrderButton;
    private Button updateOrderButton;

    @Autowired
    public UpdateOrderFormView(OrderService orderService) {
        this.orderService = orderService;

        // Create UI components
        orderIdField = new TextField("Order ID (for update)");
        orderIdField.setEnabled(false);
        costOfOrderField = new TextField("Cost of Order");
        dayOfTheWeekField = new ComboBox<>("Day of the Week");
        dayOfTheWeekField.setItems("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        restaurantNameField = new TextField("Restaurant Name");
        foodPreparationTimeField = new IntegerField("Food Preparation Time (minutes)");
        deliveryTimeField = new IntegerField("Delivery Time (minutes)");
        customerRatingField = new TextField("Customer Rating (0-5)");

           updateOrderButton = new Button("Update Order", event -> {
            orderIdField.setEnabled(true);
            updateOrder();
        });
        Button backButton = new Button("Back to Orders", e -> getUI().ifPresent(ui -> ui.navigate(OrderListView.class)));

        FormLayout formLayout = new FormLayout();
        formLayout.add(orderIdField, costOfOrderField, dayOfTheWeekField, restaurantNameField,
                       foodPreparationTimeField, deliveryTimeField, customerRatingField,
                        updateOrderButton, backButton);

        add(formLayout);
    }

    private void updateOrder() {
        if (validateFields(true)) {
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
                getUI().ifPresent(ui -> ui.navigate(OrderListView.class));
            } catch (Exception e) {
                Notification.show("Error updating order: " + e.getMessage());
            }
        }
    }

    private boolean validateFields(boolean isUpdate) {
        try {
            // Only validate orderIdField if updating an order
            if (isUpdate && !orderIdField.getValue().isEmpty()) {
                Integer.parseInt(orderIdField.getValue());
            }

            // Validate Integer fields
            if (foodPreparationTimeField.getValue() == null) {
                throw new NumberFormatException("Food Preparation Time is required.");
            }
            if (deliveryTimeField.getValue() == null) {
                throw new NumberFormatException("Delivery Time is required.");
            }

            // Validate Float fields
            if (!costOfOrderField.getValue().isEmpty()) {
                Float.parseFloat(costOfOrderField.getValue());
            }
            if (!customerRatingField.getValue().isEmpty()) {
                float rating = Float.parseFloat(customerRatingField.getValue());
                if (rating < 0 || rating > 5) {
                    throw new NumberFormatException("Customer Rating must be between 0 and 5.");
                }
            }

            return true;
        } catch (NumberFormatException e) {
            Notification.show("Please ensure fields have valid numeric values: " + e.getMessage());
            return false;
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
