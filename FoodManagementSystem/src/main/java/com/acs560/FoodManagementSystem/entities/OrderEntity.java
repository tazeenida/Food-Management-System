package com.acs560.FoodManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.acs560.FoodManagementSystem.entities.CustomerEntity;
import com.acs560.FoodManagementSystem.entities.RestaurantEntity;

@Entity
@Table(name="Orders")
@Data
@NoArgsConstructor
public class OrderEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private float costOfOrder;
	private String dayOfTheWeek;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name= "customer_id")
	private CustomerEntity customerId;
	@ManyToOne
	@NotNull
	@JoinColumn(name="restaurant_id")
	private RestaurantEntity restaurantId;
}