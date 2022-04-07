package com.example.ecommerce_a.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Order {
	Integer id;
	Integer userId;
	Integer status;
	Integer totalPrice;
	Date orderDate;
	String destinationName;
	String destinationEmail;
	String destinationZipcode;
	String destinationAddress;
	String destinationTel;
	Timestamp deliveryTime;
	Integer paymentMethod;
	User user;
	List<OrderItem> orderItemList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	public int getTax() {
		int taxTotal=0;
		for(OrderItem orderItem :orderItemList) {
			taxTotal+=orderItem.getSubTotal();
		}
		taxTotal*=0.1;
		return taxTotal;
		}
	
	public int getCalcTotalPrice() {
		int total=0;
		for(OrderItem orderItem :orderItemList) {
			total+=orderItem.getSubTotal();
		}
		total*=1.1;
		return total;
		}

	@Override
	public String toString() {
		return "Order [deliveryTime=" + deliveryTime + ", destinationAddress=" + destinationAddress
				+ ", destinationEmail=" + destinationEmail + ", destinationName=" + destinationName
				+ ", destinationTel=" + destinationTel + ", destinationZipcode=" + destinationZipcode + ", id=" + id
				+ ", orderDate=" + orderDate + ", orderItemList=" + orderItemList + ", paymentMethod=" + paymentMethod
				+ ", status=" + status + ", totalPrice=" + totalPrice + ", user=" + user + ", userId=" + userId + "]";
	}

		
}
