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

        orderIdField = new TextField("Order ID (for deletion)");
        orderIdField.setPlaceholder("Enter the Order ID");
        orderIdField.setWidth("300px"); // Control the width of the input field

        deleteOrderButton = new Button("Delete Order", event -> deleteOrder());
        backButton = new Button("Back to Orders", e -> getUI().ifPresent(ui -> ui.navigate(OrderListView.class)));

        // Align buttons and input field properly
        deleteOrderButton.addClassName("primary-button");  // Style for primary action
        backButton.addClassName("secondary-button");       // Style for secondary action

        // Arrange components in a form layout with some custom spacing
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)  // Make the form layout responsive
        );
        formLayout.add(orderIdField, deleteOrderButton, backButton);
        formLayout.setColspan(deleteOrderButton, 2); // Make delete button span across columns for better alignment

        // Add the form layout to the parent layout
        add(formLayout);
        
        // Center the content vertically and horizontally
        setAlignItems(Alignment.CENTER);  // Center align all components in the layout
        setJustifyContentMode(JustifyContentMode.CENTER); // Ensure vertical alignment too
        setSizeFull(); // Optional: makes the layout fill the screen vertically
        setSpacing(true); // Add spacing between components
        setPadding(true); // Add padding to the layout

        // Add margin to ensure the form is well centered and not sticking to the screen's edge
        setMargin(true);
    }

    /**
     * Deletes the order specified by the order ID input in the order ID field.
     * Validates the input field and displays a notification based on the operation's success or failure.
     */
    private void deleteOrder() {
        if (validateFields()) {
            try {
                Integer orderId = Integer.parseInt(orderIdField.getValue());
                orderService.delete(orderId);
                Notification.show("Order deleted successfully!");
                clearFields();
                getUI().ifPresent(ui -> ui.navigate(OrderListView.class));
            } catch (Exception e) {
                Notification.show("Error deleting order: " + e.getMessage());
            }
        }
    }

    /**
     * Validates the input fields in the form.
     *
     * @return true if the order ID is valid and not empty, false otherwise
     */
    private boolean validateFields() {
        try {
            if (orderIdField.getValue().isEmpty()) {
                Notification.show("Order ID must not be empty.");
                return false;
            }
            Integer.parseInt(orderIdField.getValue());
            return true;
        } catch (NumberFormatException e) {
            Notification.show("Please ensure the Order ID is a valid number.");
            return false;
        }
    }

    /**
     * Clears the input fields in the form.
     */
    private void clearFields() {
        orderIdField.clear();
    }
}
