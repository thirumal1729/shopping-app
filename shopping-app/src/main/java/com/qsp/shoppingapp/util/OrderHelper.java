package com.qsp.shoppingapp.util;

import java.util.List;

import com.qsp.shoppingapp.entity.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHelper {

	private Orders orders;
	private List<Integer> productIds;
	
}
