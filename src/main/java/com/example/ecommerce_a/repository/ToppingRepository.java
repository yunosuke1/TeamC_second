package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;

@Repository
public class ToppingRepository {
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

    public List<Topping> findAll(){
        String sql="SELECT * FROM toppings";
        List<Topping> toppingList=template.query(sql,TOPPING_ROW_MAPPER);
        return toppingList;

    }

    public void insert(OrderTopping orderTopping) {
    	String sql="INSERT INTO order_toppings(topping_id,order_item_id)VALUES(:toppingId,:orederItemId);";
        SqlParameterSource param=new MapSqlParameterSource().addValue("toppingId", orderTopping.getToppingId()).addValue("orederItemId", orderTopping.getOrderItemId());
        template.update(sql,param);
        
    }
}
