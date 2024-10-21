package com.acs560.FoodManagementSystem.views.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.acs560.FoodManagementSystem.entities.OrderEntity;
import com.acs560.FoodManagementSystem.services.OrderService;
import com.acs560.FoodManagementSystem.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
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

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Orders | Food Management System")
public class OrderListView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    @Autowired
    private OrderService orderService;

    private final Grid<OrderEntity> grid;
    private final TextField filterText;

    public OrderListView(OrderService orderService) {
        this.orderService = orderService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        filterText = createFilter();

        add(createToolbar(filterText), grid);
        updateGrid(); // Load initial order data
    }

    private Grid<OrderEntity> createGrid() {
        Grid<OrderEntity> grid = new Grid<>(OrderEntity.class);
        grid.addClassNames("order-grid");
        grid.setSizeFull();
        
        // Set columns based on the fields in OrderEntity
        grid.setColumns("orderId", "costOfOrder", "dayOfTheWeek", "customer", "restaurant");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        return grid;
    }

    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setValueChangeTimeout(1000);
        filterText.setPlaceholder("Filter by order ID...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        return filterText;
    }

    private Component createToolbar(TextField filterText) {
        var toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

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
}
