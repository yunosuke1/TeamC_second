package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.PasswordReset;

@Repository
public class PasswordResetRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	public void insert(PasswordReset reset) {
		String insertSql = "insert into password_resets(user_email, unique_url) values(:userEmail, :uniqueUrl);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(reset);
		template.update(insertSql, param);
	}

}
