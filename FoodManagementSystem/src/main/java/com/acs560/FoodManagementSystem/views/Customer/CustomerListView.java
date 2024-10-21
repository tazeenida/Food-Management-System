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

    public CustomerListView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        filterText = createFilter();

        add(createToolbar(filterText), grid);
        updateGrid(); // Load initial customer data
    }

    private Grid<CustomerEntity> createGrid() {
        Grid<CustomerEntity> grid = new Grid<>(CustomerEntity.class);
        grid.addClassNames("customer-grid");
        grid.setSizeFull();
        grid.setColumns("customerId", "rating"); 
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        return grid;
    }

    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setValueChangeTimeout(1000);
        filterText.setPlaceholder("Filter by customer ID...");
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
        List<CustomerEntity> customers;

        if (filter == null || filter.isEmpty()) {
            customers = customerService.getAll();
        } else {
            try {
                Integer customerId = Integer.parseInt(filter);
                customers = customerService.getByCustomerId(customerId).map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                customers = List.of(); // Return an empty list if the input is invalid
            }
        }

        grid.setItems(customers);
    }
}
