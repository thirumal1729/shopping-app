package com.qsp.shoppingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.shoppingapp.dao.ProductDao;
import com.qsp.shoppingapp.dao.UserDao;
import com.qsp.shoppingapp.dto.ResponseStructure;
import com.qsp.shoppingapp.entity.Product;
import com.qsp.shoppingapp.entity.Review;
import com.qsp.shoppingapp.entity.User;
import com.qsp.shoppingapp.exception.InvalidUserOperationException;
import com.qsp.shoppingapp.exception.ProductNotFoundException;
import com.qsp.shoppingapp.exception.UserDoesNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	public ResponseEntity<ResponseStructure<Product>> saveProduct(int userId, Product product) {
		User user = userDao.getUserById(userId);
		if(user.getRole().equalsIgnoreCase("merchant")) {
			productDao.saveProduct(userId, product);
			ResponseStructure<Product> responseStructure = new ResponseStructure<Product>();
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(product);
			
			return new ResponseEntity<ResponseStructure<Product>>(responseStructure, HttpStatus.CREATED);
			
		} else {
			throw new InvalidUserOperationException();
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts(int userId) {
		List<Product> products = productDao.getProductsByUserId(userId);
		if(!products.isEmpty()) {
			ResponseStructure<List<Product>> responseStructure = new ResponseStructure<List<Product>>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(products);
			
			return new ResponseEntity<ResponseStructure<List<Product>>>(responseStructure, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException();
		}
		
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteReviewByReviewId(int userId, int productId) {
		User user = userDao.getUserById(userId);
		Product product = productDao.getProductById(productId);
		if(user == null) {
			throw new UserDoesNotFoundException();
		} else if(user.getRole().equalsIgnoreCase("merchant")) {
			User productUser = product.getUser();
			List<Product> products = productUser.getProducts();
			if(products.contains(productUser)) {
				boolean isDeleted = productDao.deleteProductById(productId);
				if(isDeleted) {
					ResponseStructure<String> responseStructure = new ResponseStructure<>();
					responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
					responseStructure.setMessage("Success");
					responseStructure.setData("Product deleted successfully");
					
					return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
				} else {
					throw new ProductNotFoundException("No products to delete");
				}
			} else {
				throw new ProductNotFoundException("No products");
			}
			
		} else {
			throw new InvalidUserOperationException();
		}
	}
	
}
