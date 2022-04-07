package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;

@Repository
public class OrderItemRepository {
    @Autowired
	private NamedParameterJdbcTemplate template;
    
    private static final RowMapper<OrderItem> ORDERITEM_ID_ROW_MAPPER=(rs,i)->{
        OrderItem orderItem=new OrderItem();
        orderItem.setId(rs.getInt("id"));
        return orderItem;
    };
    private static final RowMapper<Order> ORDER_ID_ROW_MAPPER=(rs,i)->{
        Order order=new Order();
        order.setId(rs.getInt("id"));
        return order;
    };
    private static final RowMapper<Order> ORDER_ROW_MAPPER=(rs,i)->{
        Order order=new Order();
        order.setId(rs.getInt("id"));

        return order;
    };

    /**
    * ユーザid 確認処理
    * @param id
    * @return
    */

    public OrderItem load(Integer id) {
    	OrderItem orderItem = new OrderItem();
        String sql="SELECT id FROM orders WHERE user_id = :userId AND status = 0; ";
        SqlParameterSource param=new MapSqlParameterSource().addValue("userId", id);
        List<Order> orderList=template.query(sql, param, ORDER_ROW_MAPPER);
        if(orderList.size()==0) {
        	orderItem = null;
        	return orderItem;
        }
        Order order = orderList.get(0);
        orderItem.setOrderId(order.getId());
        return orderItem;
    }
    
    /**
     * カート追加処理
     * ordersテーブルにインサート
     */
    
     @Transactional
     public int insert(OrderItem orderItem){
    	String sql="INSERT INTO order_items(item_id,order_id,quantity,size) VALUES(:itemId,:orderId,:quantity,:size) RETURNING id;";
        SqlParameterSource param=new MapSqlParameterSource().addValue("orderId", orderItem.getOrderId()).addValue("itemId", orderItem.getItemId()).addValue("quantity", orderItem.getQuantity()).addValue("size",orderItem.getSize());
        OrderItem orderItemId=template.queryForObject(sql,param,ORDERITEM_ID_ROW_MAPPER);
        return orderItemId.getId();
     }
     
     public int insertOrder(Integer userId) {
    	String sql="INSERT INTO orders(user_id,status,total_price) values(:userId,0,0) RETURNING id;";
    	SqlParameterSource param=new MapSqlParameterSource().addValue("userId", userId);
    	Order orderId = template.queryForObject(sql,param,ORDER_ID_ROW_MAPPER);
    	return orderId.getId();
     }
     
}
