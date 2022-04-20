package com.example.ecommerce_a.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
	
	@BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Autowired
   	private NamedParameterJdbcTemplate template;
	
	@Test
	@DisplayName("ユーザーIDなし")
	void orderFinish() throws Exception {
		//コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(get("/finished"))
				.andExpect(view().name("forward:/login"))
				.andReturn();
	}
	
	@Test
	@DisplayName("ユーザーIDありfinishedIdなし")
	void orderFinish2() throws Exception {
		//コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(post("/finished")
				.sessionAttr("userId", 1))
				.andExpect(view().name("forward:/confirm"))
				.andReturn();
	}
	
	@Test
	@DisplayName("ユーザーIDありfinishedIdあり")
	void orderFinish3() throws Exception {
		//コントローラ呼び出し
		MvcResult mvcResult = mockMvc.perform(post("/finished")
				.sessionAttr("userId", 1)
				.sessionAttr("finished", 1))
				.andExpect(view().name("order_finished"))
				.andReturn();
	}
	

}
