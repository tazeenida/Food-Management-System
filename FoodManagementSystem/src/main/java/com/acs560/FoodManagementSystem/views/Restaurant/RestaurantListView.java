package com.acs560.FoodManagementSystem.views.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.acs560.FoodManagementSystem.entities.RestaurantEntity;
import com.acs560.FoodManagementSystem.models.Restaurant;
import com.acs560.FoodManagementSystem.services.RestaurantService;
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
 * The {@link RestaurantListView} class is responsible for displaying a list of
 * restaurants within the Food Management System. It provides functionalities to
 * filter restaurants by name and view their details.
 * 
 * <p>
 * This view includes a grid to display restaurant entities, a form for editing
 * restaurant details, and a filter field for searching restaurants by their name.
 * </p>
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "restaurant", layout = MainLayout.class)
@PageTitle("Restaurants | Food Management System")
public class RestaurantListView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RestaurantService restaurantService;

	private final Grid<RestaurantEntity> grid;
	private final TextField filterText;
	private final RestaurantFormView restaurantForm;

	/**
	 * Constructs a new instance of {@link RestaurantListView}. Initializes the view
	 * with a grid to display restaurants, a form to edit restaurant details, and a
	 * filter field for searching restaurants by name.
	 *
	 * @param restaurantService the service to handle restaurant-related operations
	 */
	public RestaurantListView(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;

		addClassName("list-view");
		setSizeFull();

		grid = createGrid();
		restaurantForm = createForm();
		filterText = createFilter();

		add(createToolbar(filterText), getContent());
		updateGrid();
		closeForm();
	}

	/**
	 * Creates and returns a form view for displaying and editing restaurant details.
	 * The form is initially hidden and can be made visible when a restaurant is selected.
	 * 
	 * @return the created {@link RestaurantFormView} component
	 */
	private RestaurantFormView createForm() {
		RestaurantFormView restaurantForm = new RestaurantFormView();
		restaurantForm.setVisible(false);
		return restaurantForm;
	}

	/**
	 * Creates and returns a grid for displaying restaurant entities.
	 * The grid shows the restaurant's ID, name, food preparation time, and delivery time.
	 * 
	 * @return the created {@link Grid} component for restaurant entities
	 */
	private Grid<RestaurantEntity> createGrid() {
		Grid<RestaurantEntity> grid = new Grid<>(RestaurantEntity.class);
		grid.addClassNames("restaurant-grid");
		grid.setSizeFull();
		grid.setColumns("restaurantId", "restaurantName", "foodPreparationTime", "deliveryTime");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		return grid;
	}

	/**
	 * Creates and returns a text field for filtering restaurants by name.
	 * The filter text field allows the user to type a name and filter restaurants
	 * dynamically. The search is delayed by a timeout for debounce effect.
	 * 
	 * @return the created {@link TextField} component for filtering restaurants
	 */
	private TextField createFilter() {
		TextField filterText = new TextField();
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.setValueChangeTimeout(1000); // Delay for debounce effect
		filterText.addValueChangeListener(e -> updateGrid());

		return filterText;
	}

	/**
	 * Creates and returns a toolbar containing the filter text field.
	 * The toolbar is a horizontal layout that houses the filter text field.
	 * 
	 * @param filterText the {@link TextField} used for filtering restaurants
	 * @return the created {@link HorizontalLayout} containing the filter text field
	 */
	private Component createToolbar(TextField filterText) {
		var toolbar = new HorizontalLayout(filterText);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	/**
	 * Creates and returns a layout containing the grid and the restaurant form.
	 * The content layout is a horizontal layout that arranges the grid and form
	 * side by side, with flexible space allocation.
	 * 
	 * @return the created {@link HorizontalLayout} component containing the grid and form
	 */
	private HorizontalLayout getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, restaurantForm);
		content.setFlexGrow(2, grid);
		content.setFlexGrow(1, restaurantForm);
		content.addClassNames("content");
		content.setSizeFull();
		return content;
	}

	/**
	 * Updates the grid with the list of restaurants based on the current filter text.
	 * If the filter is empty or too short, all restaurants are displayed. Otherwise,
	 * the grid is populated with restaurants that match the filter text.
	 */
	private void updateGrid() {
	    String filter = filterText.getValue().trim();

	    // Only trigger filtering if filter length is 2 or more characters
	    if (filter.length() >= 2) {
	        // Use the service to get filtered restaurants with case-insensitive matching
	        List<RestaurantEntity> filteredRestaurants = restaurantService.getByRestaurantName(filter);

	        grid.setItems(filteredRestaurants);
	    } else {
	        // If filter is empty or less than 2 characters, show all restaurants
	        grid.setItems(restaurantService.getAll());
	    }
	}

	/**
	 * Closes the restaurant form by hiding it and removing the editing class.
	 * This method ensures that no form is visible when not editing a restaurant.
	 */
	private void closeForm() {
		restaurantForm.setVisible(false);
		removeClassName("editing");
	}
}
