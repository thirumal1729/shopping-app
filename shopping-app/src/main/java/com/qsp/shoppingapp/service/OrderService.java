package com.qsp.shoppingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.shoppingapp.dao.OrderDao;
import com.qsp.shoppingapp.dao.UserDao;
import com.qsp.shoppingapp.dto.ResponseStructure;
import com.qsp.shoppingapp.entity.Orders;
import com.qsp.shoppingapp.entity.User;
import com.qsp.shoppingapp.exception.InvalidUserOperationException;
import com.qsp.shoppingapp.exception.ProductNotFoundException;
import com.qsp.shoppingapp.util.OrderHelper;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;
	
	public ResponseEntity<ResponseStructure<Orders>> saveOrder(int userId, OrderHelper orderHelper) {
		User user = userDao.getUserById(userId);
		if(user.getRole().equalsIgnoreCase("customer")) {
			Orders orders = orderDao.saveOrders(userId, orderHelper);
			ResponseStructure<Orders> responseStructure = new ResponseStructure<Orders>();
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(orders);
			
			return new ResponseEntity<ResponseStructure<Orders>>(responseStructure, HttpStatus.CREATED); 
		} else {
			throw new InvalidUserOperationException();
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrdersByUserId(int userId) {
		List<Orders> orders = orderDao.getAllOrdersByUserId(userId);
		if(!orders.isEmpty()) {
			ResponseStructure<List<Orders>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Success");
			responseStructure.setData(orders);
			
			return new ResponseEntity<ResponseStructure<List<Orders>>>(responseStructure, HttpStatus.OK);
		} else {
			throw new ProductNotFoundException("No Orders");
		}
	}
	
}
