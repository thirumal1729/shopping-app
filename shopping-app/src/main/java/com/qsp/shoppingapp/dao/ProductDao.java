package com.qsp.shoppingapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.shoppingapp.entity.Product;
import com.qsp.shoppingapp.entity.User;
import com.qsp.shoppingapp.repository.ProductRepository;

@Repository
public class ProductDao {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserDao userDao;
	
	public Product saveProduct(int userId, Product product) {
		User user = userDao.getUserById(userId);
		if(user != null) {
			product.setUser(user);
			return productRepository.save(product);
		} else {
			return null;
		}
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getProductsByUserId(int userId) {
		return productRepository.findByUserUserId(userId);
	}
	
	public Product getProductById(int productId) {
		Optional<Product> optional = productRepository.findById(productId);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	public boolean deleteProductById(int productId) {
		Product product = getProductById(productId);
		if(product != null) {
			productRepository.delete(product);
			return true;
		} else {
			return false;
		}
	}
	
}
