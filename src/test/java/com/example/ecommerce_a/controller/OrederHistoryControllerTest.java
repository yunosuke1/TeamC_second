package com.example.ecommerce_a.controller;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
class OrederHistoryControllerTest {
	
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

    @Test
	@DisplayName("ユーザIDがない場合")
    void NoUserId() throws Exception {
        mockMvc.perform(get("/orderHistory"))
                .andExpect(view().name("forward:/login"))
                .andReturn();
    }
    
    
    @Test
	@DisplayName("注文履歴がある場合")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx")
    void withOrderHistory() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/orderHistory")
    			.sessionAttr("userId", 1)
    			).andExpect(view().name("order_history"))
    			.andReturn();
    	
    	ModelAndView mav = mvcResult.getModelAndView();
    	@SuppressWarnings(value = "unchecked")// 下のキャストのワーニングを出さないようにする
        List<OrderItem> orderList = (List<OrderItem>) mav.getModel().get("orderHistory");
//    	assertEquals("Specialキャラメルドーナッツ",orderList.get(0).getItem().getName());
    }

    
    @Test
	@DisplayName("注文履歴がない場合")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx", type = DatabaseOperation.DELETE_ALL)
    void noOrderHistory() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/orderHistory")
    			.sessionAttr("userId", 1)
    			).andExpect(view().name("order_history"))
    			.andReturn();
    	
    	ModelAndView mav = mvcResult.getModelAndView();
    	String emptymessage = (String) mav.getModel().get("emptyMessage");
    	assertEquals("注文履歴がありません",emptymessage);
    }
}
    