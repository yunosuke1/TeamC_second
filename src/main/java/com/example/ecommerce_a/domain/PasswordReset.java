package com.example.ecommerce_a.domain;

public class PasswordReset {
	private Integer id;
	private String userEmail;
	private String uniqueUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUniqueUrl() {
		return uniqueUrl;
	}

	public void setUniqueUrl(String uniqueUrl) {
		this.uniqueUrl = uniqueUrl;
	}

}