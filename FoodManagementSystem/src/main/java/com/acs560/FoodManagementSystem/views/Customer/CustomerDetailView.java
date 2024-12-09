package com.acs560.FoodManagementSystem.views.Customer;

import com.acs560.FoodManagementSystem.models.Customer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

/**
 * The {@link CustomerDetailView} class represents a form for displaying and editing customer details.
 * <p>
 * This form is bound to a {@link Customer} object and includes a field for the customer's rating.
 * The form is responsible for displaying the customer data and performing validation using 
 * {@link Binder} and {@link BeanValidationBinder}. It extends {@link FormLayout} to manage the 
 * layout and binding of fields.
 * </p>
 */
public class CustomerDetailView extends FormLayout {

    private static final long serialVersionUID = 476310807171214015L;

    private final TextField rating = new TextField("Rating");

    private final Binder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    /**
     * Constructs a new instance of {@link CustomerDetailView}.
     * <p>
     * This constructor initializes the form layout, binds the form fields to the properties 
     * of the {@link Customer} object using a {@link Binder}, and sets the width of the form.
     * </p>
     */
    public CustomerDetailView() {
        addClassName("customer-detail");

        binder.bindInstanceFields(this);

        add(rating);
        setWidth("25em");
    }

    /**
     * Updates the form with the specified customer's data for display purposes.
     * <p>
     * This method binds the provided {@link Customer} object to the form fields, allowing 
     * the form to display the customer data.
     * </p>
     *
     * @param customer the {@link Customer} object containing data to be displayed in the form
     */
    public void setCustomer(Customer customer) {
        binder.readBean(customer);
    }

    /**
     * Clears the form fields when no customer is selected.
     * <p>
     * This method resets the form to its initial state, clearing all data fields.
     * It is typically used when the form is cleared or when a new customer is selected.
     * </p>
     */
    public void clear() {
        binder.readBean(null);
    }
}
