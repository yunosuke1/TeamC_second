package com.example.ecommerce_a.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;

@SpringBootTest
class OrderRepositoryTest {

	@Autowired
	private OrderRepository repository;
	
//	@Autowired
//	private NamedParameterJdbcTemplate template;
//
//	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
//		Order order = new Order();
//		order.setId(rs.getInt("id"));
//		order.setUserId(rs.getInt("user_id"));
//		order.setStatus(rs.getInt("status"));
//		order.setTotalPrice(rs.getInt("total_price"));
//		order.setOrderDate(rs.getDate("order_date"));
//		order.setDestinationName(rs.getString("destination_name"));
//		order.setDestinationEmail(rs.getString("destination_email"));
//		order.setDestinationZipcode(rs.getString("zipcode"));
//		order.setDestinationTel(rs.getString("destination_tel"));
//		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
//		order.setPaymentMethod(rs.getInt("payment_method"));
//
//		return order;
//	};

	@Test
	void testFindOrderItemList() {
		List<OrderItem> orderItemList = repository.findOrderItemList(1);
		orderItemList.forEach(s -> assertEquals(1, s.getQuantity()));
	}

	@Test
	void testDeleteCart() {
		repository.deleteCart(1);
	}

	@Test
	void testDeleteOrder() {
		repository.deleteOrder(1);
	}

	@Test
	void testUpdateOrder() {
		fail("Not yet implemented");
	}

	@Test
	void testDestinationUpdate() {
		Order order = new Order();
		order.setUserId(1);
		order.setStatus(1);
		order.setTotalPrice(2000);
		Date date = new Date();
		order.setOrderDate(date);
		order.setDestinationName("テスト");
		order.setDestinationEmail("aaa@gmail.com");
		order.setDestinationZipcode("222-2222");
		order.setDestinationAddress("神奈川県横浜市港北区");
		order.setDestinationTel("010-0000-0000");
		Timestamp date2 = new Timestamp(date.getTime());
		order.setDeliveryTime(date2);
		order.setPaymentMethod(1);
		repository.destinationUpdate(order);
	}

	@Test
	void testFindOrderHistory() {
		List<OrderItem> orderItemList = repository.findOrderItemList(1);
		orderItemList.forEach(s -> assertEquals(1, s.getQuantity()));
	}

}
