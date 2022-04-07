package com.example.ecommerce_a.service;

import java.util.List;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.repository.OrderHisoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderHistoryService {
    @Autowired
    private OrderHisoryRepository   repository;

    public  List<Order> findOrderHistory(int userId){
        return repository.findOrderHistory(userId);
    }
    
}
