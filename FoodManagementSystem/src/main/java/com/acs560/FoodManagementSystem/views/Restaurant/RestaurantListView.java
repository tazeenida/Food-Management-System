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

	public RestaurantListView(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;

		addClassName("list-view");
		setSizeFull();

		grid = createGrid();
		restaurantForm = createForm();
		filterText = createFilter();

		add(createToolbar(filterText), getContent());
		updateGrid(); // Load initial restaurant data
		closeForm();
	}

	private RestaurantFormView createForm() {
		RestaurantFormView restaurantForm = new RestaurantFormView();
		restaurantForm.setVisible(false); // Initially hide the form
		return restaurantForm;
	}

	private Grid<RestaurantEntity> createGrid() {
		Grid<RestaurantEntity> grid = new Grid<>(RestaurantEntity.class);
		grid.addClassNames("restaurant-grid");
		grid.setSizeFull();
		grid.setColumns("restaurantId", "restaurantName", "foodPreparationTime", "deliveryTime");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		return grid;
	}

	private TextField createFilter() {
		TextField filterText = new TextField();
		filterText.setValueChangeTimeout(1000);
		filterText.setPlaceholder("Filter by name...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		filterText.addValueChangeListener(e -> updateGrid()); // Call updateGrid on value change

		return filterText;
	}

	private Component createToolbar(TextField filterText) {
		var toolbar = new HorizontalLayout(filterText);
		toolbar.addClassName("toolbar");
		return toolbar;
	}

	private HorizontalLayout getContent() {
		HorizontalLayout content = new HorizontalLayout(grid, restaurantForm);
		content.setFlexGrow(2, grid);
		content.setFlexGrow(1, restaurantForm);
		content.addClassNames("content");
		content.setSizeFull();
		return content;
	}

	private void updateGrid() {
		String filter = filterText.getValue();
		List<RestaurantEntity> restaurants;

		if (filter == null || filter.isEmpty()) {
			restaurants = restaurantService.getAll();
		} else {
			restaurants = restaurantService.getByRestaurantName(filter);
		}

		grid.setItems(restaurants);
	}

	private void closeForm() {
		restaurantForm.setVisible(false);
		removeClassName("editing");
	}
}
