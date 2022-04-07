package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.repository.OrderRepository;

@Service
public class CartService {
	@Autowired
	private OrderRepository repository;
	
	public List<OrderItem> findOrderItemList(Integer userId){
		return repository.findOrderItemList(userId);
	}
	
	public void deleteCart(Integer deleteId){
		repository.deleteCart(deleteId);
	}
	public	List<OrderItem>	findOrderHistory(int userId){
		return	repository.findOrderHistory(userId);
	}
	
}