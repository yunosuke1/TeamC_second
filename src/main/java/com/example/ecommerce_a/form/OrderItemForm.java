package com.example.ecommerce_a.form;

import java.util.List;

public class OrderItemForm {

	private Integer itemId;
	private Integer quantity;
	private String size;
	private List<String>orderToppings;
	
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "OrderItemForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size + "]";
	}
	public List<String> getOrderToppings() {
		return orderToppings;
	}
	public void setOrderToppings(List<String> orderToppings) {
		this.orderToppings = orderToppings;
	}

	
	
	
	
}
