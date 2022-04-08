package com.example.ecommerce_a.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.util.SessionUtil;
import com.example.ecommerce_a.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import org.junit.jupiter.api.Test;
@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})


class UserPageControllerTest {
	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    @Autowired
   	private NamedParameterJdbcTemplate template;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @AfterEach
    void tearDown() throws Exception {
    }
    
	
	/**
     * 登録系のサンプルその１
     *
     * 登録、更新系は、perform(post())を使用
     * このアプリでは登録確認画面をはさむらしく、入力した値はいきなりDBには登録されずsessionに一旦格納するらしい
     * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
     */
    @Test
    @DisplayName("登録確認画面に遷移")
    void userPage_01() throws Exception {
        mockMvc.perform(post("/userPage")).andExpect(view().name("forward:/login"))
                ;

    }
    
    /**
     * 登録系のサンプルその１
     *
     * 登録、更新系は、perform(post())を使用
     * このアプリでは登録確認画面をはさむらしく、入力した値はいきなりDBには登録されずsessionに一旦格納するらしい
     * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
     */
    @Test
    @DisplayName("登録確認画面に遷移")
    void userPage_02() throws Exception {
    	MockHttpSession userSession01 = SessionUtil.userSession02();

    	mockMvc.perform(get("/userPage")
    			.session(userSession01)
    			);
    	
    }


}
