package com.acs560.FoodManagementSystem.views.Customer;

import com.acs560.FoodManagementSystem.models.Customer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

/**
 * The form to display customer details.
 */
public class CustomerDetailView extends FormLayout {

    private static final long serialVersionUID = 476310807171214015L;

    private final TextField name = new TextField("Name");
    private final TextField location = new TextField("Location"); // Assuming location as a field

    private final Binder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    /**
     * Constructor
     */
    public CustomerDetailView() {
        addClassName("customer-detail");

        binder.bindInstanceFields(this);

        add(name, location);  // Add the fields to the form layout
        setWidth("25em");
    }

    /**
     * Update the form with customer data for display purposes.
     * @param customer - the customer to be displayed
     */
    public void setCustomer(Customer customer) {
        binder.readBean(customer); // Bind the customer data to the form fields
    }

    /**
     * Clear form fields when no customer is selected.
     */
    public void clear() {
        binder.readBean(null);  // Clear the form when no customer is selected
    }
}
