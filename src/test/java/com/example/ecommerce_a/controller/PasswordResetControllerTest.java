package com.example.ecommerce_a.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.domain.PasswordReset;
import com.example.ecommerce_a.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class
})

class PasswordResetControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	private MockMvc mockMvc;
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DisplayName("画面遷移")
	//コントローラ呼び出し
	void resetP1() throws Exception{
		mockMvc.perform(get("/reset"))
		.andExpect(view().name("password_reset"))
		.andReturn();
	}
	
	@Test
	@DisplayName("アカウントが存在しない場合")
	//コントローラ呼び出し
	void resetP2() throws Exception{
		mockMvc.perform(get("/reset/resetConfirm"))
		.andExpect(view().name("password_reset"))
		.andReturn();
	}
	
	@Test
	@DisplayName("アカウントが存在する場合")
	//コントローラ呼び出し
	void resetP3() throws Exception{
		mockMvc.perform(get("/reset/resetConfirm")
		.param("userEmail", "test3@example.jp")
		)
		.andExpect(view().name("email_submit"))
		.andReturn();
	}
	
	@Test
	@DisplayName("emailが不正の場合")
	//コントローラ呼び出し
	void resetP4() throws Exception{
		mockMvc.perform(get("/reset/resetConfirm")
		.param("userEmail", "test.co.jp"));
	}
	
	@Test
	@DisplayName("パスワードリセット完了OK")
	//コントローラ呼び出し
	void resetP5() throws Exception{
		PasswordReset passwordReset = new PasswordReset();
		String sql = "truncate table password_resets";
		SqlParameterSource param1 = new BeanPropertySqlParameterSource(passwordReset);
		template.update(sql, param1);
		
		mockMvc.perform(get("/reset/passwordResetFinished")
				.param("key", "fff")
				.param("getNewPassword", "kkkkkkkk")
				.param("getConfirmpassword", "kkkkkkkk")
				.param("newPassword", "kkkkkkkk")
				.param("confirmpassword", "kkkkkkkk")
				.sessionAttr("userEmail", "test3@example.jp")
				.sessionAttr("userEmailPass", "test3@example.jp")
				.param("id", "3")
				.sessionAttr("uniqueUrl", "kkkkkkkk")
				)
		.andExpect(view().name("reset_finished"))
		.andReturn();
	}

	@Test
	@DisplayName("key")
	//コントローラ呼び出し
	void resetP6() throws Exception{
		mockMvc.perform(get("/reset/passwordReset")
				.param("key", "fff")
				.param("getNewPassword", "kkkkkkkk")
				.param("getConfirmpassword", "kkkkkkko")
				.param("newPassword", "kkkkkkkk")
				.param("confirmpassword", "kkkkkkko")
				)
		.andExpect(view().name("reset_password"))
		.andReturn();
	}
	
	@Test
	@DisplayName("パスワードリセット完了後パスワード不一致")
	//コントローラ呼び出し
	void resetP7() throws Exception{
		mockMvc.perform(get("/reset/passwordResetFinished")
		.param("key", "fff")
		.param("getNewPassword", "kkkkkkkk")
		.param("getConfirmpassword", "kkkkkkko")
		.param("newPassword", "kkkkkkkk")
		.param("confirmpassword", "kkkkkkko")
		)
		.andExpect(view().name("reset_password"))
		.andReturn();
	}
}
