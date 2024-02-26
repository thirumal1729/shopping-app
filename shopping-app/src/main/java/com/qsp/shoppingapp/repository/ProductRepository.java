package com.qsp.shoppingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.shoppingapp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public List<Product> findByUserUserId(int id);
	
}
