package com.qsp.shoppingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.shoppingapp.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

	public List<Orders> findByUserUserId(int id);
	
}
