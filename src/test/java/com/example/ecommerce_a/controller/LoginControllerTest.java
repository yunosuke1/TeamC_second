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
import org.springframework.security.crypto.password.PasswordEncoder;
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
class LoginControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
    void withUserId() throws Exception {
        // ①コントローラー呼出し
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andReturn();
    }

	@Test
	@DisplayName("ユーザIdがある場合")
	void noUserId() throws Exception {
	    // ①コントローラー呼出し
	    mockMvc.perform(post("/login")
	    		.sessionAttr("userId", 1)
	    		).andExpect(view().name("redirect:/shoppingList"))
	            .andReturn();
	}
    
	@Test
	@DisplayName("ログイン情報が間違っている場合")
	@DatabaseSetup(value = "classpath:user/LoginTestUser_01.xlsx")
    void withLoginInfo_01() throws Exception {
       // ①コントローラー呼出し
    	MvcResult mvcResult = mockMvc.perform(post("/login/complete")
       		.param("email", "noregister.test.com")
       		.param("password", "wrong_password")
       		).andExpect(view().name("login"))
             .andReturn();
    	
    	ModelAndView mav = mvcResult.getModelAndView();
    	String errorMessage = (String) mav.getModel().get("errorMessage");
    	
    	assertEquals("メールアドレス、またはパスワードが間違っています",errorMessage);
    }
	
	@Test
	@DisplayName("ログイン情報が正しい場合(preIdがnull)")
	@DatabaseSetup(value = "classpath:user/LoginTestUser_01.xlsx")
    void withLoginInfo_02() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/login/complete")
    		.param("id","1")
       		.param("email", "test@example.jp")
       		.param("password", "Abcdefg123")
       		.param("zipcode", "111-1111")
       		.param("address", "テスト住所")
       		.param("telephone", "090-9999-9999")
       		).andExpect(view().name("redirect:/shoppingList"))
             .andReturn();
    }
	
	@Test
	@DisplayName("ログイン情報が正しい場合(preIdとuseIdがあり、orderListが空ではない)")
	@DatabaseSetup(value = "classpath:user/LoginTestUser_01.xlsx")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx")
	@ExpectedDatabase(value = "classpath:order/order_history_02.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void withLoginInfo_03() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/login/complete")
    		.sessionAttr("preId", 1)
    		.sessionAttr("userId", 1)
    		.param("id","1")
       		.param("email", "test@example.jp")
       		.param("password", "Abcdefg123")
       		.param("zipcode", "111-1111")
       		.param("address", "テスト住所")
       		.param("telephone", "090-9999-9999")
       		).andExpect(view().name("redirect:/shoppingList"))
             .andReturn();
    }
	
	@Test
	@DisplayName("ログイン情報が正しい場合(preIdとuseIdがあり、orderListが空)")
	@DatabaseSetup(value = "classpath:user/LoginTestUser_01.xlsx")
	@DatabaseSetup(value = "classpath:order/order_history.xlsx", type = DatabaseOperation.DELETE_ALL)
    void withLoginInfo_04() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/login/complete")
    		.sessionAttr("preId", 1)
    		.sessionAttr("userId", 1)
    		.param("id","1")
       		.param("email", "test@example.jp")
       		.param("password", "Abcdefg123")
       		.param("zipcode", "111-1111")
       		.param("address", "テスト住所")
       		.param("telephone", "090-9999-9999")
       		).andExpect(view().name("redirect:/shoppingList"))
             .andReturn();
    }
    
    
}
    