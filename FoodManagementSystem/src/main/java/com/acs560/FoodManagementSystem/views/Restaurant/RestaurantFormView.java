package com.acs560.FoodManagementSystem.views.Restaurant;

import com.acs560.FoodManagementSystem.models.Restaurant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;

/**
 * The {@link RestaurantFormView} class provides a form layout for displaying
 * and updating restaurant details within the Food Management System.
 * This view allows users to input and modify the name, food preparation time,
 * and delivery time of a restaurant.
 * 
 * <p>
 * The form includes fields for each of these details and is designed to be reused
 * for both displaying existing restaurant information and allowing the user to update
 * these details.
 * </p>
 */
public class RestaurantFormView extends FormLayout {

    private final TextField restaurantName = new TextField("Restaurant Name");
    private final TextField foodPreparationTime = new TextField("Food Preparation Time (minutes)");
    private final TextField deliveryTime = new TextField("Delivery Time (minutes)");

    private Restaurant restaurant;

    /**
     * Constructs a new instance of {@link RestaurantFormView}.
     * This constructor initializes the form layout and adds input fields for the
     * restaurant details, such as name, food preparation time, and delivery time.
     */
    public RestaurantFormView() {
        add(restaurantName, foodPreparationTime, deliveryTime);
        setWidth("25em");
    }

    /**
     * Updates the form with the provided restaurant details.
     * If the given restaurant is not null, the form fields are populated with the
     * restaurant's information. If the restaurant is null, the fields are cleared.
     *
     * @param restaurant the {@link Restaurant} object containing the details to display
     *                   in the form, or null to clear the fields
     */
    public void update(Restaurant restaurant) {
        this.restaurant = restaurant;

        if (restaurant != null) {
            restaurantName.setValue(restaurant.getRestaurantName());
            foodPreparationTime.setValue(restaurant.getFoodPreparationTime() != null ? restaurant.getFoodPreparationTime().toString() : "");
            deliveryTime.setValue(restaurant.getDeliveryTime() != null ? restaurant.getDeliveryTime().toString() : "");
        } else {
            restaurantName.clear();
            foodPreparationTime.clear();
            deliveryTime.clear();
        }
    }
}
