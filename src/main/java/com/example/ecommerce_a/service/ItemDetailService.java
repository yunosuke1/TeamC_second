package com.example.ecommerce_a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.repository.OrderItemRepository;
import com.example.ecommerce_a.repository.ToppingRepository;

@Service
@Transactional
public class ItemDetailService {
    @Autowired
    private ToppingRepository toppingRepository;
    @Autowired
    private OrderItemRepository orderItemrepository;
    
    public List<Topping> findAll(){
        return toppingRepository.findAll();
    }

    public void insertTopping(OrderTopping orderTopping) {
    	toppingRepository.insert(orderTopping);
    }
    
    public OrderItem load(Integer id) {
    	return orderItemrepository.load(id);
    }
    
    public int insertOrderItem(OrderItem orderItem) {
    	return orderItemrepository.insert(orderItem);
    }
    
    public int insertOrder(Integer userId) {
    	return orderItemrepository.insertOrder(userId);
    }
    

}
