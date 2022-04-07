package com.example.ecommerce_a.repository;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
 
/**
 * @author smone
 *
 */
@Repository
public class ItemRepository {
    @Autowired
    private NamedParameterJdbcTemplate template;
   
    private static final RowMapper<Item> ITEM_ROW_MAPPER=(rs,i)->{
        Item item=new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setImagePath(rs.getString("image_path"));
        item.setPriceM(rs.getInt("price_m"));
        item.setPriceL(rs.getInt("price_l"));
        item.setDescription(rs.getString("description"));
 
        return item;
 
    };
   
    /**
     * 全件検索を行う（価格昇順）
     * @return 商品一覧
     */
    public List<Item> findAll(){
        String sql="SELECT id,name,price_m,price_l,image_path,description FROM items ORDER BY  price_m  ASC ;";
        List<Item> itemList=template.query(sql, ITEM_ROW_MAPPER);
       
        return itemList;
    }
 
    /**
     * 全件検索を行う(価格降順)
     * @return 商品一覧
     */
 
    public List<Item> findAllDesc(){
        String sql="SELECT id,name,price_m,price_l,image_path,description FROM items ORDER BY  price_m  DESC ;";
        List<Item> itemList=template.query(sql, ITEM_ROW_MAPPER);
       
        return itemList;
    }
   

   
    /**
     * 商品の曖昧検索を行う（価格昇順）
     * @param searchWord
     * @return　商品一覧
     */
    public List<Item> findByLikeName(String searchWord) {
        String sql="SELECT id,name,price_m,price_l,image_path,description FROM items WHERE name like :name ORDER BY  price_m  ASC ;";
        SqlParameterSource param=new MapSqlParameterSource().addValue("name", "%"+searchWord+"%");
        List<Item> itemList=template.query(sql, param, ITEM_ROW_MAPPER);
        return itemList;
    }
 
    /**
     * 商品の曖昧検索を行う（価格降順）
     * @param searchWord
     * @return　商品一覧
     */
    
    public List<Item> findByLikeNameDesc(String searchWord) {
        String sql="SELECT id,name,price_m,price_l,image_path,description FROM items WHERE name like :name ORDER BY  price_m  DESC ;";
        SqlParameterSource param=new MapSqlParameterSource().addValue("name", "%"+searchWord+"%");
        List<Item> itemList=template.query(sql, param, ITEM_ROW_MAPPER);
        return itemList;
    }
 
 
    public Item load(Integer id) {
        String sql="SELECT id,name,  price_m,price_l,image_path,description FROM items WHERE id=:id";
        SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
        Item item=template.queryForObject(sql, param, ITEM_ROW_MAPPER);
        return item;
    }
}

