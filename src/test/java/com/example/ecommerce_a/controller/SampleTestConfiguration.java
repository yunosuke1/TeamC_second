package com.example.ecommerce_a.controller;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
class SampleTestConfiguration {

	@Bean
	public DataSource dataSource() {
		return new TransactionAwareDataSourceProxy(
			DataSourceBuilder
			.create()
			.username("postgres")
			.password("postgres")
			.url("jdbc:postgresql://localhost:5432/ec-202110b-test")
			.driverClassName("org.postgresql.Driver")
			.build());
	}
}
