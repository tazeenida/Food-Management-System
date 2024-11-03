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

@PermitAll
@Route(value = "delete-order-form")
@PageTitle("Delete Order | Food Management System")
public class DeleteOrderFormView extends VerticalLayout {

	private final OrderService orderService;

	private TextField orderIdField;
	private Button deleteOrderButton;

	@Autowired
	public DeleteOrderFormView(OrderService orderService) {
		this.orderService = orderService;

		orderIdField = new TextField("Order ID (for deletion)");

		deleteOrderButton = new Button("Delete Order", event -> deleteOrder());
		Button backButton = new Button("Back to Orders",
				e -> getUI().ifPresent(ui -> ui.navigate(OrderListView.class)));

		FormLayout formLayout = new FormLayout();
		formLayout.add(orderIdField, deleteOrderButton, backButton);

		add(formLayout);
	}

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

	private void clearFields() {
		orderIdField.clear();
	}
}
