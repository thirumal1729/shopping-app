package com.qsp.shoppingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.shoppingapp.dao.ReviewDao;
import com.qsp.shoppingapp.dao.UserDao;
import com.qsp.shoppingapp.dto.ResponseStructure;
import com.qsp.shoppingapp.entity.Product;
import com.qsp.shoppingapp.entity.Review;
import com.qsp.shoppingapp.entity.User;
import com.qsp.shoppingapp.exception.InvalidUserOperationException;
import com.qsp.shoppingapp.exception.PlaceOrderToReviewException;
import com.qsp.shoppingapp.exception.ProductNotFoundException;
import com.qsp.shoppingapp.exception.UserDoesNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	
	@Autowired
	private UserDao userDao;
	
	public ResponseEntity<ResponseStructure<Review>> saveReview(int userId, int productId, Review review) {
		User user = userDao.getUserById(userId);
		if(user == null) {
			throw new UserDoesNotFoundException();
		} else if(user.getRole().equalsIgnoreCase("customer")) {
			Review reviews = reviewDao.saveReview(productId, review);
			if(reviews != null) {
				ResponseStructure<Review> responseStructure = new ResponseStructure<Review>();
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Success");
				responseStructure.setData(review);
				
				return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.CREATED);
			} else {
				throw new PlaceOrderToReviewException();
			}
		} else {
			throw new InvalidUserOperationException();
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Review>>> getAllReviewsBYProductId(int productId) {
		List<Review> reviews = reviewDao.getAllReviews(productId);
		if(!reviews.isEmpty()) {
			ResponseStructure<List<Review>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(reviews);
			
			return new ResponseEntity<ResponseStructure<List<Review>>>(responseStructure, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("No reviews");
		}
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteReviewByReviewId(int userId, int ReviewId) {
		User user = userDao.getUserById(userId);
		Review review = reviewDao.getReviewById(ReviewId);
		if(user == null) {
			throw new UserDoesNotFoundException();
		} else if(user.getRole().equalsIgnoreCase("merchant")) {
			Product product = review.getProduct();
			List<Product> products = user.getProducts();
			if(products.contains(product)) {
				boolean isDeleted = reviewDao.deleteReviewById(ReviewId);
				if(isDeleted) {
					ResponseStructure<String> responseStructure = new ResponseStructure<>();
					responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
					responseStructure.setMessage("Success");
					responseStructure.setData("Review deleted successfully");
					
					return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
				} else {
					throw new ProductNotFoundException("No reviews to delete");
				}
			} else {
				throw new ProductNotFoundException("No products");
			}
			
		} else {
			throw new InvalidUserOperationException();
		}
	}
	
}
