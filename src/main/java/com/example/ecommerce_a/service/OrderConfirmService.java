package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.repository.OrderRepository;

@Service
@Transactional
public class OrderConfirmService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<OrderItem> findOrderItemList(Integer userId){
		return orderRepository.findOrderItemList(userId);
	}
	
	public void update(Order order) {
		orderRepository.destinationUpdate(order);
	}
}
