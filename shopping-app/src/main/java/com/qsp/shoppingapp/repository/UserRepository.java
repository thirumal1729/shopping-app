package com.qsp.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.shoppingapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
