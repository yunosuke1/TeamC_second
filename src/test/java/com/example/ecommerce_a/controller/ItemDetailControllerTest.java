package com.example.ecommerce_a.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class
})

class ItemDetailControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	private MockMvc mockMvc;
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DisplayName("画面遷移")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	void syousai1() throws Exception {
		OrderItem orders = new OrderItem();
		String sql = "truncate table orders";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(orders);
		template.update(sql, param1);
		
		mockMvc.perform(get("/itemDetail")
		.param("id", "1")
		.param("toppingList", "1")
		
		)
		.andExpect(view().name("item_detail"))
		.andReturn();
	}

	@Test
	@DisplayName("トッピングなしOK")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	void syousai2() throws Exception {
		OrderItem orders = new OrderItem();
		String sql = "truncate table order_items restart identity";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(orders);
		template.update(sql, param1);
		
		mockMvc.perform(get("/itemDetail/insert")
				.param("id", "1")
				.param("itemId", "1")
				.param("item","1")
//				.sessionAttr("itemId",1)
				.param("quantity", "1")
				.sessionAttr("orderId", 1)
				.sessionAttr("preId", 1)
				.sessionAttr("userId", 1)
				.param("toppingId", "1")
				.param("order_date", "2222-22-22-22")
				.param("size", "M")
				.param("size", "M")
				.param("size", "M")
				.param("size", "M")
				)
				.andExpect(view().name("redirect:/shoppingCart"))
				.andReturn();
	}
	
	@Test
	@DisplayName("トッピングありOK")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	void syousai3() throws Exception {
		OrderItem orders = new OrderItem();
		String sql = "truncate table order_items restart identity";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(orders);
		template.update(sql, param1);
		
		mockMvc.perform(get("/itemDetail/insert")
				.param("id", "1")
				.param("itemId", "1")
				.param("item","1")
//				.sessionAttr("itemId",1)
				.param("quantity", "1")
				.sessionAttr("orderId", 1)
				.sessionAttr("preId", 1)
				.sessionAttr("userId", 1)
				.param("orderToppings", "1")
				.param("order_date", "2222-22-22-22")
				.param("size", "M")
				.param("size", "M")
				.param("size", "M")
				.param("size", "M")
				)
				.andExpect(view().name("redirect:/shoppingCart"))
				.andReturn();
	}
	
	@Test
	@DisplayName("userIdなし")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	void syousai4() throws Exception {
		OrderItem orders = new OrderItem();
		String sql = "truncate table order_items restart identity";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(orders);
		template.update(sql, param1);
		
		mockMvc.perform(get("/itemDetail/insert")
				.param("id", "1")
				.param("itemId", "1")
				.param("item","1")
				.sessionAttr("itemId",1)
				.param("quantity", "1")
				.sessionAttr("orderId", 1)
				.sessionAttr("preId", 1)
//				.sessionAttr("userId", 1)
				.param("orderToppings", "1")
				.param("order_date", "2222-22-22-22")
				.param("size", "M")
//				.param("size", "M")
//				.param("size", "M")
//				.param("size", "M")
				)
				.andExpect(view().name("redirect:/shoppingCart"))
				.andReturn();
	}
	
	@Test
	@DisplayName("userIdなしpreあり")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	void syousai5() throws Exception {
//		OrderItem orders = new OrderItem();
		OrderItem orders = new OrderItem();
		String sql = "truncate table orders restart identity";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(orders);
		template.update(sql, param1);
		
		OrderItem orders1 = new OrderItem();
		String sql1 = "truncate table order_items restart identity";
		SqlParameterSource param2 = new BeanPropertySqlParameterSource(orders1);
		template.update(sql1, param2);
		
		mockMvc.perform(get("/itemDetail/insert")
				.param("id", "1")
				.param("itemId", "1")
				.param("item","1")
				.sessionAttr("itemId",1)
				.param("quantity", "1")
				.sessionAttr("orderId", 1)
//				.sessionAttr("preId", 1)
//				.sessionAttr("userId", 1)
				.param("orderToppings", "1")
				.param("order_date", "2222-22-22-22")
				.param("size", "M")
//				.param("size", "M")
//				.param("size", "M")
//				.param("size", "M")
				)
				.andExpect(view().name("redirect:/shoppingCart"))
				.andReturn();
	}
	
	@Test
	@DisplayName("getNameなし")
//	@DatabaseSetup(value = "classpath:order/itemDetail_01.xlsx")
	void syousai6() throws Exception {
		Item items = new Item();
		String sql = "alter table items alter column name drop not null;"
				+ "update items set name = null where id = 31;";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(items);
		template.update(sql, param1);
		
		mockMvc.perform(get("/itemDetail")
		.param("id", "31")
		
		
		)
		.andExpect(view().name("redirect:/shoppingList"))
		.andReturn();
	}
	
	@Test
	@DisplayName("orderItemなし")
//	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	void syousai7() throws Exception {
		OrderItem orders = new OrderItem();
		String sql = "truncate table orders restart identity";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(orders);
		template.update(sql, param1);
//		
		mockMvc.perform(get("/itemDetail/insert")
				.param("id", "9")
				.param("itemId", "9")
				.param("item","9")
				.sessionAttr("itemId",100)
				.param("quantity", "1")
				.sessionAttr("orderId", 100)
				.sessionAttr("preId", 100)
				.sessionAttr("userId", 7)
//				.param("toppingId", "1")
				.param("order_date", "2222-22-22-22")
				.param("size", "M")
				.param("size", "M")
//				.param("size", "M")
//				.param("size", "M")
				)
				.andExpect(view().name("redirect:/shoppingCart"))
				.andReturn();
	}
}
