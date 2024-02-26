package com.qsp.shoppingapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.shoppingapp.entity.Orders;
import com.qsp.shoppingapp.entity.Product;
import com.qsp.shoppingapp.entity.Review;
import com.qsp.shoppingapp.repository.ReviewRepository;

@Repository
public class ReviewDao {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ProductDao productDao;
	
	public Review saveReview(int productId, Review review) {
		Product product = productDao.getProductById(productId);
		if(product != null) {
			List<Orders> orders = product.getOrders();
			if(orders.isEmpty()) {
				return null;
			} else {
				review.setProduct(product);
				return reviewRepository.save(review);
			}
		} else {
			return null;
		}
	}
	
	public Review getReviewById(int reviewId) {
		Optional<Review> optional = reviewRepository.findById(reviewId);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	public List<Review> getAllReviews(int productId) {
		return reviewRepository.findByProductProductId(productId);
	}
	
	public boolean deleteReviewById(int reviewId) {
		Review review = getReviewById(reviewId);
		if(review != null) {
			reviewRepository.delete(review);
			return true;
		} else {
			return false;
		}
	}
}
