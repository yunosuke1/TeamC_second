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
        DependencyInjectionTestExecutionListener.class, // ???????????????????????????DI???????????????????????????
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetup???@ExpectedDatabase?????????????????????????????????
})


class UserPageControllerTest {
	@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
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
     *
     */
    @Test
    @DisplayName("???????????????????????????(???????????????????????????)")
    void userPage_01() throws Exception {
        mockMvc.perform(post("/userPage")).andExpect(view().name("forward:/login"))
                ;
    }
    
    /**
     *
     */
    @Test
    @DisplayName("????????????????????????????????????(???????????????????????????)")
    void userPage_02() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession04();
        mockMvc.perform(get("/userPage")
        		.session(userSession)
        		).andExpect(view().name("user_page"))
				.andReturn();
    }


}
