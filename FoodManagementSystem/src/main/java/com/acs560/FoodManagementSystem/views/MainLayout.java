package com.acs560.FoodManagementSystem.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.acs560.FoodManagementSystem.models.Order;
import com.acs560.FoodManagementSystem.securities.SecurityService;
import com.acs560.FoodManagementSystem.views.Customer.CustomerListView;
import com.acs560.FoodManagementSystem.views.Order.OrderHistoryView;
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
import com.acs560.FoodManagementSystem.views.CacheLogsView;

/**
 * The {@link MainLayout} class represents the main layout for the Food Management System application.
 * It extends {@link AppLayout} and provides a header and a navigation drawer for easy navigation between different views.
 * 
 * The layout includes a logo, a user-specific logout button, and navigation links to various sections of the application such as
 * Orders, Order History, Customers, Restaurants, and Cache Logs. The layout is designed to offer a user-friendly interface
 * for managing and accessing different aspects of the Food Management System.
 */
public class MainLayout extends AppLayout {
    private static final long serialVersionUID = -5291741451913578403L;

    @Autowired
    private final SecurityService securityService;

    /**
     * Constructs a new instance of {@link MainLayout}.
     * Initializes the main layout by setting up the header and the navigation drawer.
     *
     * @param securityService the {@link SecurityService} used for handling user authentication and logout
     */
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    /**
     * Creates the header for the layout. The header contains the application logo and a logout button.
     * The logout button displays the authenticated user's username and allows the user to log out.
     */
    private void createHeader() {
        H1 logo = new H1("Food Management System");
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);
        
        String username = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Log out " + username, e -> securityService.logout());
        
        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
        
        addToNavbar(header);
    }

    /**
     * Creates the navigation drawer for the layout. The drawer contains links to the various views available in the application.
     * These links provide easy access to different sections of the Food Management System, including:
     * - Orders
     * - Order History
     * - Cache Logs
     * - Customers
     * - Restaurants
     */
    private void createDrawer() {
        RouterLink orderLink = new RouterLink("Orders", OrderListView.class);
        orderLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink orderHistoryLink = new RouterLink("Order History", OrderHistoryView.class);
        RouterLink cacheLogsLink = new RouterLink("Cache Logs", CacheLogsView.class);
        
        RouterLink customerLink = new RouterLink("Customers", CustomerListView.class);
        RouterLink restaurantLink = new RouterLink("Restaurants", RestaurantListView.class);
        
        addToDrawer(new VerticalLayout(orderLink, customerLink, restaurantLink, orderHistoryLink, cacheLogsLink));
    }
}
