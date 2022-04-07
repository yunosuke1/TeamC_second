package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.repository.OrderRepository;

@Service
public class LoginService {
    @Autowired
    private OrderItemRepository orderItemrepository;
    @Autowired
    private OrderRepository orderRepository;
    
    
	public OrderItem load(Integer id) {
    	return orderItemrepository.load(id);
    }
	
	public void deleteOrder(Integer id) {
    	orderRepository.deleteOrder(id);
    }
	
	public void updateOrder(Integer userId,Integer preId) {
		orderRepository.updateOrder(userId,preId);
	}
	
}
