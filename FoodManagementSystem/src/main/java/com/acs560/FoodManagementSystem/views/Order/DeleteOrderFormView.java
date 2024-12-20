package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.services.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The {@link DeleteOrderFormView} class provides a user interface for deleting an order in the Food Management System.
 * It extends {@link VerticalLayout} and contains a form that allows users to input an order ID for deletion.
 * The form includes a field to enter the Order ID and buttons to perform the deletion or navigate back to the order list.
 * This view ensures that only valid order IDs are entered for deletion, and it provides feedback on the operation status.
 */
@PermitAll
@Route(value = "delete-order-form")
@PageTitle("Delete Order | Food Management System")
public class DeleteOrderFormView extends VerticalLayout {

    private final OrderService orderService;

    private TextField orderIdField;
    private Button deleteOrderButton;
    private Button backButton;

    /**
     * Constructs a new instance of {@link DeleteOrderFormView}.
     * Initializes the UI components, including the order ID field and buttons for deletion and navigation.
     *
     * @param orderService the service used to manage order-related operations
     */
    @Autowired
    public DeleteOrderFormView(OrderService orderService) {
        this.orderService = orderService;

        // Initialize form components
        orderIdField = new TextField("Order ID (for deletion)");
        orderIdField.setPlaceholder("Enter the Order ID");
        orderIdField.setWidth("300px"); // Control the width of the input field

        deleteOrderButton = new Button("Delete Order", event -> deleteOrder());
        backButton = new Button("Back to Orders", e -> getUI().ifPresent(ui -> ui.navigate(OrderListView.class)));

        // Align buttons and input field properly
        deleteOrderButton.addClassName("primary-button");  // Style for primary action
        backButton.addClassName("secondary-button");       // Style for secondary action

        // Create and configure the form layout
        FormLayout formLayout = new FormLayout();
        formLayout.add(orderIdField, deleteOrderButton, backButton);

        // Set form width and styling for consistency
        formLayout.setWidth("100%");
        formLayout.getStyle().set("max-width", "400px").set("margin", "auto");

        // Add the form layout to the main layout
        add(formLayout);
        setAlignItems(Alignment.CENTER);  // Center align all the items
        setSizeFull();  
    }

    /**
     * Deletes the order specified by the order ID input in the order ID field.
     * Validates the input field and displays a notification based on the operation's success or failure.
     * If the deletion is successful, the fields are cleared, and the user is navigated back to the order list.
     */
    private void deleteOrder() {
        if (validateFields()) {
            try {
                Integer orderId = Integer.parseInt(orderIdField.getValue());
                orderService.delete(orderId); // Delete the order using the order service
                Notification.show("Order deleted successfully!"); // Show success notification
                clearFields(); // Clear the input fields after successful deletion
                getUI().ifPresent(ui -> ui.navigate(OrderListView.class)); // Navigate back to order list
            } catch (Exception e) {
                Notification.show("Error deleting order: " + e.getMessage()); // Show error notification
            }
        }
    }

    /**
     * Validates the input fields in the form.
     * Ensures that the order ID is not empty and is a valid number.
     *
     * @return true if the order ID is valid and not empty, false otherwise
     */
    private boolean validateFields() {
        try {
            if (orderIdField.getValue().isEmpty()) {
                Notification.show("Order ID must not be empty."); // Show notification if field is empty
                return false;
            }
            Integer.parseInt(orderIdField.getValue()); // Ensure the order ID is a valid number
            return true;
        } catch (NumberFormatException e) {
            Notification.show("Please ensure the Order ID is a valid number."); // Show notification for invalid number
            return false;
        }
    }

    /**
     * Clears the input fields in the form.
     * This is typically called after a successful operation (like deletion).
     */
    private void clearFields() {
        orderIdField.clear(); // Clear the order ID field
    }
}
