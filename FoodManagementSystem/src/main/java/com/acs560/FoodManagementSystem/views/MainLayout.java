package com.acs560.FoodManagementSystem.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.securities.SecurityService;
import com.acs560.FoodManagementSystem.views.Customer.CustomerListView;
import com.acs560.FoodManagementSystem.views.Order.OrderListView;
import com.acs560.FoodManagementSystem.views.Restaurant.RestaurantListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    private static final long serialVersionUID = -5291741451913578403L;

    @Autowired
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Food Management System");
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);
        
        // Get authenticated username
        String username = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Log out " + username, e -> securityService.logout());
        
        // Create header layout
        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
        
        // Add the header to the navbar
        addToNavbar(header);
    }

    private void createDrawer() {
        // Create navigation links
        RouterLink orderLink = new RouterLink("", OrderListView.class);
        orderLink.setHighlightCondition(HighlightConditions.sameLocation());
        
        RouterLink customerLink = new RouterLink("Customers", CustomerListView.class);
        RouterLink restaurantLink = new RouterLink("Restaurants", RestaurantListView.class);
        
        // Add links to the drawer
        addToDrawer(new VerticalLayout(orderLink, customerLink, restaurantLink));
    }
}
