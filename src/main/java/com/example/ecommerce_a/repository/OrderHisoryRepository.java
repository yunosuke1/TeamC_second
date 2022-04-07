package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;

@Repository
public class OrderHisoryRepository {
	
		// ResultSetオブジェクトに格納された複数行分のデータをList<Clab>変数にセットしてreturnする
		private static final ResultSetExtractor<List<Order>> ORDER_ITEM_RESULTSET = (rs) -> {
			// 初めにデータを格納するための変数を宣言
			List<Order> orderList = new ArrayList<>();
	
			// メンバーを格納するためのList<Member>変数を宣言(値はNullを格納しておく)
			List<OrderItem> orderItemList = null;
	
			// clabsテーブルは結合した際に複数行にわたり同じデータが出力される可能性があるため、前のClabテーブルのIDを保持するための変数を宣言
			int beforeIdNum = 0;
	
			// ResultSetオブジェクトに格納された複数のデータをList<Clab>変数に格納していく
			while (rs.next()) {
				// 現在検索しているClabテーブルのIDを格納するための変数を宣言
				int nowIdNum = rs.getInt("ord_id");
	
				// 現在検索しているClabテーブルのIDと前のClabテーブルのIDが違う場合は新たにClabオブジェクトを作成する
				if (nowIdNum != beforeIdNum) {
					Order order = new Order();
					order.setStatus(rs.getInt("ord_status"));
					order.setOrderDate(rs.getDate("ord_order_date"));
					order.setId(rs.getInt("ord_id"));
					order.setTotalPrice(rs.getInt("ord_total_price"));
					// メンバーがいた際にClabオブジェクトのmemberListに格納するため空のArrayListをセットしておく
					orderItemList = new ArrayList<OrderItem>();
					order.setOrderItemList(orderItemList);
					orderList.add(order);
				}
				// ClabにMemberがいない場合はMemberオブジェクトを作成しないようにする
				if (rs.getInt("ori_id") != 0) {
					OrderItem orderItem = new OrderItem();
					orderItem.setId(nowIdNum);
					orderItem.setQuantity(rs.getInt("ori_quantity"));
					char[] c = rs.getString("ori_size").toCharArray();
					orderItem.setSize(c[0]);
	
					Item item = new Item();
					item.setName(rs.getString("itm_name"));
					item.setPriceM(rs.getInt("itm_price_m"));
					item.setPriceL(rs.getInt("itm_price_l"));
					item.setImagePath(rs.getString("itm_image_path"));
					item.setId(rs.getInt("itm_id"));
					orderItem.setItem(item);
					// メンバーがいた際にClabオブジェクトのmemberListに格納するため空のArrayListをセットしておく
					orderItemList.add(orderItem);
				}
	
			  //現在検索しているClabテーブルのIDを前のClabテーブルのIDを入れるbeforeIdNumに代入する
			  beforeIdNum = nowIdNum;
			}
			return orderList;
		  };


	@Autowired
	private NamedParameterJdbcTemplate template;

	
	public	List<Order> findOrderHistory(int userId){
		String	sql="SELECT ord.status ord_status,ord.total_price ord_total_price,ord,ord.order_date ord_order_date,"
		+" ord.id ord_id,ori.id ori_id,ori.quantity ori_quantity,ori.size ori_size,itm.name itm_name,itm.id itm_id,itm.price_m itm_price_m,itm.price_l itm_price_l,itm.image_path itm_image_path "
		+ "FROM orders ord "
		+ "JOIN order_items ori ON ord.id=ori.order_id "
		+ "JOIN items itm ON ori.item_id = itm.id "
		+ "WHERE ord.user_id=:userId AND (ord.status=1 OR ord.status=2);";


		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId",userId);
		List<Order> orderList = template.query(sql,param,ORDER_ITEM_RESULTSET);
		return orderList;
	}

}
