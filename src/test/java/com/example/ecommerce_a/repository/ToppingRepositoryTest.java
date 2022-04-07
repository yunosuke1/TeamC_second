package com.example.ecommerce_a.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;
@SpringBootTest
class ToppingRepositoryTest {

	@Autowired
	private ToppingRepository repository;
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER=(rs,i)->{
        Topping topping=new Topping();
        topping.setId(rs.getInt("id"));
        topping.setName(rs.getString("name"));
        topping.setPriceL(rs.getInt("price_l"));
        topping.setPriceM(rs.getInt("price_m"));
        return topping;
    };
    
	@Test
	void testFindAll() {
		List<Topping> toppinglList = repository.findAll();
		assertEquals("コーヒークリーム", toppinglList.get(0).getName(),"違います");
	}

	@Test
	void testInsert() {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setToppingId(1);
		orderTopping.setOrderItemId(1);
		repository.insert(orderTopping);
        
	}

}
