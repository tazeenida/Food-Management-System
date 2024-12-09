package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

/**
 * The {@link UpdateOrderFormView} class provides a user interface for updating
 * an existing order in the Food Management System. It allows users to modify
 * various details of an order, including cost, day of the week, restaurant name,
 * preparation time, delivery time, and customer rating.
 * 
 * <p>
 * The view consists of a form with fields for each of these details, and a button
 * to submit the update. After a successful update, the user is redirected to the
 * order list view.
 * </p>
 * 
 * <p>
 * This view is accessible via the {@code "update-order-form"} route and requires
 * that the user is authorized to update orders.
 * </p>
 */
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
    private Button updateOrderButton;

    /**
     * Constructs a new instance of {@link UpdateOrderFormView}.
     * 
     * @param orderService the service used to update order details
     */
    @Autowired
    public UpdateOrderFormView(OrderService orderService) {
        this.orderService = orderService;

        // Initialize fields
        orderIdField = new TextField("Order ID (for update)");
        orderIdField.setEnabled(true);
        costOfOrderField = new TextField("Cost of Order");
        dayOfTheWeekField = new ComboBox<>("Day of the Week");
        dayOfTheWeekField.setItems("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        restaurantNameField = new TextField("Restaurant Name");
        foodPreparationTimeField = new IntegerField("Food Preparation Time (minutes)");
        deliveryTimeField = new IntegerField("Delivery Time (minutes)");
        customerRatingField = new TextField("Customer Rating (0-5)");

        // Initialize buttons
        updateOrderButton = new Button("Update Order", event -> updateOrder());
        updateOrderButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button backButton = new Button("Back to Orders", e -> getUI().ifPresent(ui -> ui.navigate(OrderListView.class)));

        // Create the form layout
        FormLayout formLayout = new FormLayout();
        formLayout.add(orderIdField, costOfOrderField, dayOfTheWeekField, restaurantNameField, 
                       foodPreparationTimeField, deliveryTimeField, customerRatingField, 
                       updateOrderButton, backButton);

        // Set form width and styling for consistency
        formLayout.setWidth("100%");
        formLayout.getStyle().set("max-width", "400px").set("margin", "auto");

        // Add the form layout to the main layout
        add(formLayout);
        setAlignItems(Alignment.CENTER);  // Center align all the items
        setSizeFull();  // Ensure the layout takes up the full screen height
    }

    /**
     * Updates the order with the new information provided by the user.
     * This method retrieves the new values from the form fields, validates them,
     * and then calls the {@link OrderService} to update the order in the system.
     * 
     * @throws NumberFormatException if invalid data is entered for numeric fields
     */
    private void updateOrder() {
        if (validateFields(true)) {
            try {
                Integer orderId = Integer.parseInt(orderIdField.getValue());
                Order updatedOrder = new Order();

                String costOfOrderValue = costOfOrderField.getValue();
                if (!costOfOrderValue.isEmpty()) {
                    updatedOrder.setCostOfOrder(Float.parseFloat(costOfOrderValue));
                }

                String dayOfTheWeekValue = dayOfTheWeekField.getValue();
                if (dayOfTheWeekValue != null && !dayOfTheWeekValue.isEmpty()) {
                    updatedOrder.setDayOfTheWeek(dayOfTheWeekValue);
                }

                String restaurantName = restaurantNameField.getValue();
                Integer foodPreparationTime = foodPreparationTimeField.getValue();
                Integer deliveryTime = deliveryTimeField.getValue();
                String customerRatingValue = customerRatingField.getValue();

                Float customerRating = null;
                if (customerRatingValue != null && !customerRatingValue.isEmpty()) {
                    customerRating = Float.parseFloat(customerRatingValue);
                }

                orderService.updateOrder(orderId, updatedOrder, restaurantName, foodPreparationTime, deliveryTime, customerRating);
                Notification.show("Order updated successfully!");
                clearFields();
                getUI().ifPresent(ui -> ui.navigate(OrderListView.class));
            } catch (Exception e) {
                Notification.show("Error updating order: " + e.getMessage());
            }
        }
    }

    /**
     * Validates the input fields to ensure that the order ID is a valid integer.
     * Displays an error notification if validation fails.
     * 
     * @param isUpdate indicates whether the operation is an update
     * @return true if all fields are valid, false otherwise
     */
    private boolean validateFields(boolean isUpdate) {
        try {
            if (isUpdate && !orderIdField.getValue().isEmpty()) {
                Integer.parseInt(orderIdField.getValue());
            }
            return true;
        } catch (NumberFormatException e) {
            Notification.show("Please ensure fields have valid numeric values: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clears all the input fields in the form.
     */
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
