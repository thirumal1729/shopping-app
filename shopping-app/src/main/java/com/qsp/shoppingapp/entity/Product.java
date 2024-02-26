package com.qsp.shoppingapp.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private double productCost;
	private String productType;
	
	@OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<Review> reviews;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "products")
	private List<Orders> orders = new ArrayList<Orders>();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private User user;
	
}
