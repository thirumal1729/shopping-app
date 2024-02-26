package com.qsp.shoppingapp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.shoppingapp.entity.Orders;
import com.qsp.shoppingapp.entity.Product;
import com.qsp.shoppingapp.entity.User;
import com.qsp.shoppingapp.repository.OrderRepository;
import com.qsp.shoppingapp.util.OrderHelper;

@Repository
public class OrderDao {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	public Orders saveOrders(int userId, OrderHelper orderHelper) {
		User user = userDao.getUserById(userId);
		Orders order = orderHelper.getOrders();
		if(user != null) {
			List<Product> products = new ArrayList<Product>();
			for (int integer : orderHelper.getProductIds()) {
				Product product = productDao.getProductById(integer);
				if(product != null) {
					products.add(product);
				}
			}
			order.setProducts(products);
			order.setUser(user);
			return orderRepository.save(order);
		} else {
			return null;
		}
	}
	
	public Orders getOrdersById(int orderId) {
		Optional<Orders> optional = orderRepository.findById(orderId);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	public List<Orders> getAllOrdersByUserId(int userId) {
		return orderRepository.findByUserUserId(userId);
	}
	
}
