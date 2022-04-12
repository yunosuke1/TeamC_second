package com.example.ecommerce_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration
@TestExecutionListeners( {
	DependencyInjectionTestExecutionListener.class,// このテストクラスでDIを使えるように指定
	TransactionDbUnitTestExecutionListener.class// @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})

class OrderFinishedControllerTest {
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Autowired
   	private NamedParameterJdbcTemplate template;
	
//	@Test
	

}
