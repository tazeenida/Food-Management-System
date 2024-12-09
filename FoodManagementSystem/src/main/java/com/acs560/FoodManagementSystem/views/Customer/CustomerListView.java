package com.acs560.FoodManagementSystem.views.Customer; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.acs560.FoodManagementSystem.entities.CustomerEntity;
import com.acs560.FoodManagementSystem.services.CustomerService;
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

/**
 * The {@link CustomerListView} class provides a user interface for managing and displaying a list of customers.
 * <p>
 * This class extends {@link VerticalLayout} and utilizes a {@link Grid} to display customer data, 
 * with a filter field to search customers by their ID. The filter is applied dynamically as the user types, 
 * and the grid updates to show matching results.
 * </p>
 * <p>
 * The {@link CustomerListView} fetches customer data using the {@link CustomerService} and provides 
 * a mechanism to filter the customers by their ID.
 * </p>
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Customers | Food Management System")
public class CustomerListView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CustomerService customerService;

    private final Grid<CustomerEntity> grid;
    private final TextField filterText;

    /**
     * Constructs a new instance of {@link CustomerListView}.
     * <p>
     * This constructor initializes the customer grid and filter text field, setting up the view with 
     * the necessary components for displaying and searching customer data.
     * </p>
     *
     * @param customerService the service used to access customer data
     */
    public CustomerListView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        filterText = createFilter();

        add(createToolbar(filterText), grid);
        updateGrid();
    }

    /**
     * Creates and configures the grid for displaying customer data.
     * <p>
     * The grid is set up to show the customer ID and rating for each customer, with auto-width columns.
     * </p>
     *
     * @return a configured {@link Grid} of {@link CustomerEntity} objects
     */
    private Grid<CustomerEntity> createGrid() {
        Grid<CustomerEntity> grid = new Grid<>(CustomerEntity.class);
        grid.addClassNames("customer-grid");
        grid.setSizeFull();
        grid.setColumns("customerId", "rating"); 
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        return grid;
    }

    /**
     * Creates and configures the filter text field for searching customers.
     * <p>
     * The filter field is configured to react to user input with a 1-second delay before updating the grid, 
     * allowing for efficient searching of customer IDs.
     * </p>
     *
     * @return a configured {@link TextField} for filtering customer data
     */
    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setValueChangeTimeout(1000);
        filterText.setPlaceholder("Filter by customer ID...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        return filterText;
    }

    /**
     * Creates a toolbar containing the filter text field.
     * <p>
     * The toolbar provides the search input for filtering customers.
     * </p>
     *
     * @param filterText the filter text field to be added to the toolbar
     * @return a {@link Component} representing the toolbar containing the filter field
     */
    private Component createToolbar(TextField filterText) {
        var toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /**
     * Updates the grid with customer data based on the filter text.
     * <p>
     * If the filter is empty, all customers are displayed. 
     * If a valid customer ID is entered, the grid updates to show only the customer that matches the ID.
     * If the entered ID is invalid, the grid will show no customers.
     * </p>
     */
    private void updateGrid() {
        String filter = filterText.getValue();
        List<CustomerEntity> customers;

        if (filter == null || filter.isEmpty()) {
            customers = customerService.getAll();
        } else {
            try {
                Integer customerId = Integer.parseInt(filter);
                customers = customerService.getByCustomerId(customerId).map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                customers = List.of();
            }
        }

        grid.setItems(customers);
    }
}
