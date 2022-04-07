package com.example.ecommerce_a.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.example.ecommerce_a.domain.model.ValidGroup1;
import com.example.ecommerce_a.domain.model.ValidGroup2;

public class OrderConfirmForm {
	@NotBlank(message="名前を入力して下さい",groups = ValidGroup1.class)
	String destinationName;
	@NotBlank(message="メールアドレスを入力して下さい",groups = ValidGroup1.class)
	@Email(message="メールアドレスの形式が不正です",groups = ValidGroup2.class)
	String destinationEmail;
	@NotBlank(message="郵便番号を入力して下さい",groups = ValidGroup1.class)
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$", message="郵便番号はXXX-XXXXの形式で入力してください",groups = ValidGroup2.class)
	String destinationZipcode;
	@NotBlank(message="住所を入力して下さい",groups = ValidGroup1.class)
	String destinationAddress;
	@NotBlank(message="電話番号を入力して下さい",groups = ValidGroup1.class)
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message="電話番号はXXX-XXXX-XXXXの形式で入力してください",groups = ValidGroup2.class)
	String destinationTel;
	@NotBlank(message="配達日時を入力して下さい",groups = ValidGroup1.class)
	String deliveryDate;
	String deliveryHour;
	String paymentMethod;

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

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryHour() {
		return deliveryHour;
	}

	public void setDeliveryHour(String deliveryHour) {
		this.deliveryHour = deliveryHour;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}

