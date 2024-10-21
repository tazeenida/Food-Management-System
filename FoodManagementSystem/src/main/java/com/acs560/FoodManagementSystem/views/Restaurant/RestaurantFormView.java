package com.acs560.FoodManagementSystem.views.Restaurant;

import com.acs560.FoodManagementSystem.models.Restaurant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;

public class RestaurantFormView extends FormLayout {

    private final TextField restaurantName = new TextField("Restaurant Name");
    private final TextField foodPreparationTime = new TextField("Food Preparation Time (minutes)");
    private final TextField deliveryTime = new TextField("Delivery Time (minutes)");

    private Restaurant restaurant;

    /**
     * Constructor
     */
    public RestaurantFormView() {
        add(restaurantName, foodPreparationTime, deliveryTime);
        setWidth("25em");
    }

    /**
     * Update the form with the restaurant details
     * @param restaurant - the restaurant 
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
