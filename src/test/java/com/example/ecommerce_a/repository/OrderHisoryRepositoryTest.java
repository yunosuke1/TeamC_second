package com.example.ecommerce_a.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderHisoryRepositoryTest {

	@Autowired
	private OrderHisoryRepository repository;
	
	@Test
	void testFindOrderHistory() {
		repository.findOrderHistory(0);
		repository.findOrderHistory(1);
		repository.findOrderHistory(2);
		}
}
