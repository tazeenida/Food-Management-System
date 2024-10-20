package com.acs560.FoodManagementSystem.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
        Button button = new Button("Click Me", e -> {
            System.out.println("Button clicked!");
        });
        add(button);
    }
}
