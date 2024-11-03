package com.acs560.FoodManagementSystem.views.Order;

import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.acs560.FoodManagementSystem.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;

/**
 * The {@link OrderListView} class provides a user interface for displaying and managing orders.
 * It extends {@link VerticalLayout} and includes functionality for filtering and listing orders.
 */
@SpringComponent
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Orders | Food Management System")
@Scope("prototype")
public class OrderListView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final OrderService orderService;
    private final Grid<OrderEntity> grid;
    private final TextField filterText;
    private final Button addOrderButton;
    private final Button updateOrderButton;

    /**
     * Constructs a new instance of {@link OrderListView}.
     * Initializes the layout, grid for displaying orders, and the filter text field.
     *
     * @param orderService the service to manage order data
     */
    public OrderListView(OrderService orderService) {
        this.orderService = orderService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        filterText = createFilter();
        addOrderButton = new Button("Add Order", event -> navigateToAddOrder());
        updateOrderButton = new Button("Update Order", event -> navigateToUpdateOrder());

        add(createToolbar(filterText, addOrderButton, updateOrderButton), grid);
        updateGrid(); // Load initial order data
    }

    /**
     * Creates and configures a grid for displaying orders.
     *
     * @return a configured {@link Grid} instance for {@link OrderEntity}
     */
    private Grid<OrderEntity> createGrid() {
        Grid<OrderEntity> grid = new Grid<>(OrderEntity.class);
        grid.addClassNames("order-grid");
        grid.setSizeFull();
        
        // Set columns based on the fields in OrderEntity
        grid.setColumns("orderId", "costOfOrder", "dayOfTheWeek", "customer", "restaurant");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateOrderButton.setEnabled(event.getValue() != null)); // Enable update button if an order is selected

        return grid;
    }

    /**
     * Creates a text field for filtering orders based on the order ID.
     *
     * @return a configured {@link TextField} instance for filtering
     */
    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setValueChangeTimeout(1000);
        filterText.setPlaceholder("Filter by order ID...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        return filterText;
    }

    /**
     * Creates a toolbar containing the filter text field and action buttons.
     *
     * @param filterText the text field used for filtering orders
     * @param addOrderButton the button for adding new orders
     * @param updateOrderButton the button for updating existing orders
     * @return a {@link HorizontalLayout} containing the toolbar components
     */
    private Component createToolbar(TextField filterText, Button addOrderButton, Button updateOrderButton) {
        var toolbar = new HorizontalLayout(filterText, addOrderButton, updateOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /**
     * Updates the grid with orders based on the current filter text.
     */
    private void updateGrid() {
        String filter = filterText.getValue();
        List<OrderEntity> orders;

        if (filter == null || filter.isEmpty()) {
            orders = orderService.getAll(); // Get all orders if no filter
        } else {
            try {
                Integer orderId = Integer.parseInt(filter); // Filter by order ID
                Optional<OrderEntity> orderOpt = orderService.getByOrderId(orderId);
                orders = orderOpt.map(List::of).orElseGet(List::of); // If found, return as a single-item list, else empty list
            } catch (NumberFormatException e) {
                orders = List.of(); // Return an empty list if the input is not a valid number
            }
        }

        grid.setItems(orders); // Update the grid with filtered or all orders
    }

    private void navigateToAddOrder() {
        getUI().ifPresent(ui -> ui.navigate("add-order-form")); // Navigate to Add Order form
    }

    private void navigateToUpdateOrder() {
        getUI().ifPresent(ui -> ui.navigate("update-order-form")); // Navigate to the order form for updates
    }
}
