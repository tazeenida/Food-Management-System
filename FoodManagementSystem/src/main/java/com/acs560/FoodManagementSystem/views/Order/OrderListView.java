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
 * The {@link OrderListView} class represents the view for displaying a list of orders in the Food Management System.
 * It provides filters for order ID and customer rating, as well as options to add, update, or delete orders.
 * The view interacts with the {@link OrderService} to fetch and display the orders.
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
    private final TextField minRatingField;
    private final TextField maxRatingField;
    private final Button addOrderButton;
    private final Button updateOrderButton;
    private final Button deleteOrderButton;

    /**
     * Constructs the {@link OrderListView} with the provided {@link OrderService}.
     * Initializes the layout components, including the grid, filter fields, and action buttons.
     *
     * @param orderService The service used to fetch and manage orders.
     */
    public OrderListView(OrderService orderService) {
        this.orderService = orderService;

        addClassName("list-view");
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        grid = createGrid();
        filterText = createFilter();
        minRatingField = createMinRatingFilter();
        maxRatingField = createMaxRatingFilter();
        addOrderButton = new Button("Add Order", event -> navigateToAddOrder());
        updateOrderButton = new Button("Update Order", event -> navigateToUpdateOrder());
        deleteOrderButton = new Button("Delete Order", event -> navigateToDeleteOrder());

        var filters = createFiltersLayout(filterText, minRatingField, maxRatingField);
        var toolbar = createToolbar(addOrderButton, updateOrderButton, deleteOrderButton);

        add(filters, toolbar, grid);
        updateGrid();
    }

    /**
     * Creates the grid component that displays the list of orders.
     * The grid includes columns for order ID, cost, day of the week, customer, and restaurant.
     *
     * @return a configured {@link Grid} displaying order information.
     */
    private Grid<OrderEntity> createGrid() {
        Grid<OrderEntity> grid = new Grid<>(OrderEntity.class);
        grid.addClassNames("order-grid");
        grid.setSizeFull();
        grid.setColumns("orderId", "costOfOrder", "dayOfTheWeek", "customer", "restaurant");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> updateOrderButton.setEnabled(event.getValue() != null));
        return grid;
    }

    /**
     * Creates the filter field for searching orders by order ID.
     *
     * @return a {@link TextField} for entering the order ID filter.
     */
    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Filter by order ID...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());
        return filterText;
    }

    /**
     * Creates the filter field for specifying the minimum rating of the customer.
     *
     * @return a {@link TextField} for entering the minimum rating filter.
     */
    private TextField createMinRatingFilter() {
        TextField minRatingField = new TextField();
        minRatingField.setPlaceholder("Min rating...");
        minRatingField.setClearButtonVisible(true);
        minRatingField.setValueChangeMode(ValueChangeMode.LAZY);
        minRatingField.addValueChangeListener(e -> updateGrid());
        return minRatingField;
    }

    /**
     * Creates the filter field for specifying the maximum rating of the customer.
     *
     * @return a {@link TextField} for entering the maximum rating filter.
     */
    private TextField createMaxRatingFilter() {
        TextField maxRatingField = new TextField();
        maxRatingField.setPlaceholder("Max rating...");
        maxRatingField.setClearButtonVisible(true);
        maxRatingField.setValueChangeMode(ValueChangeMode.LAZY);
        maxRatingField.addValueChangeListener(e -> updateGrid());
        return maxRatingField;
    }

    /**
     * Creates the layout for the filter fields (order ID, min rating, and max rating).
     *
     * @param filterText The filter for order ID.
     * @param minRatingField The filter for minimum rating.
     * @param maxRatingField The filter for maximum rating.
     * @return a {@link HorizontalLayout} containing the filter fields.
     */
    private Component createFiltersLayout(TextField filterText, TextField minRatingField, TextField maxRatingField) {
        var filtersLayout = new HorizontalLayout(filterText, minRatingField, maxRatingField);
        filtersLayout.addClassName("filters-layout");
        filtersLayout.setWidthFull();
        filtersLayout.setSpacing(true);
        filtersLayout.setPadding(false);
        return filtersLayout;
    }

    /**
     * Creates the toolbar containing action buttons for adding, updating, and deleting orders.
     *
     * @param addOrderButton The button for adding a new order.
     * @param updateOrderButton The button for updating an existing order.
     * @param deleteOrderButton The button for deleting an order.
     * @return a {@link HorizontalLayout} containing the action buttons.
     */
    private Component createToolbar(Button addOrderButton, Button updateOrderButton, Button deleteOrderButton) {
        var toolbar = new HorizontalLayout(addOrderButton, updateOrderButton, deleteOrderButton);
        toolbar.addClassName("toolbar");
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.START);
        toolbar.setSpacing(true);
        toolbar.setPadding(false);
        return toolbar;
    }

    /**
     * Updates the grid to display orders based on the current filter values (order ID and customer rating range).
     * Filters the orders by order ID, and if the rating filters are provided, by the customer rating range.
     */
    private void updateGrid() {
        String filter = filterText.getValue();
        String minRating = minRatingField.getValue();
        String maxRating = maxRatingField.getValue();
        List<OrderEntity> orders;

        if (filter == null || filter.isEmpty()) {
            orders = orderService.getAll();
        } else {
            try {
                Integer orderId = Integer.parseInt(filter);
                Optional<OrderEntity> orderOpt = orderService.getByOrderId(orderId);
                orders = orderOpt.map(List::of).orElseGet(List::of);
            } catch (NumberFormatException e) {
                orders = List.of();
            }
        }

        if ((minRating != null && !minRating.isEmpty()) && (maxRating != null && !maxRating.isEmpty())) {
            try {
                float min = Float.parseFloat(minRating);
                float max = Float.parseFloat(maxRating);
                orders = orderService.getByCustomerRatingRange(min, max);
            } catch (NumberFormatException e) {
                Notification.show("Invalid rating range.");
                orders = List.of();
            }
        }

        grid.setItems(orders);
    }

    /**
     * Navigates to the add order form view.
     */
    private void navigateToAddOrder() {
        getUI().ifPresent(ui -> ui.navigate("add-order-form"));
    }

    /**
     * Navigates to the update order form view.
     */
    private void navigateToUpdateOrder() {
        getUI().ifPresent(ui -> ui.navigate("update-order-form"));
    }

    /**
     * Navigates to the delete order form view.
     */
    private void navigateToDeleteOrder() {
        getUI().ifPresent(ui -> ui.navigate("delete-order-form"));
    }
}
