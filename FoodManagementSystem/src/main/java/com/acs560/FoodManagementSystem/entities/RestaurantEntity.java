package com.acs560.FoodManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "Restaurants")
@Data
@NoArgsConstructor
public class RestaurantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Integer restaurantId;
	@NotNull
	private String restaurantName;
	private Integer foodPreparationTime;
	private Integer deliveryTime;
}