package com.qsp.shoppingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.shoppingapp.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	public List<Review> findByProductProductId(int id);
	
}
