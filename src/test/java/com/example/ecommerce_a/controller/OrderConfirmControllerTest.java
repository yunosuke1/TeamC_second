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
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
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
class OrderConfirmControllerTest {
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
     * 
     *
     */
    @Test
    @DisplayName("ログイン画面に遷移(状態：ログイン無し)")
    void confirm_01() throws Exception {
        mockMvc.perform(get("/confirm")).andExpect(view().name("forward:/login"));
    }
    
    /**
     * 
     *
     */
    @Test
    @Sql("classpath:order/re.confirm_02.sql")
    @DisplayName("ログイン画面に遷移(状態：ログイン有り、カート無し)")
    void confirm_02() throws Exception {
    	Order order = new Order();
    	String sqlOrder = "TRUNCATE TABLE orders RESTART IDENTITY;";
    	SqlParameterSource param1 = new BeanPropertySqlParameterSource(order);
 		template.update(sqlOrder, param1);
    	OrderItem item = new OrderItem();
    	String sqlOrderItem = "TRUNCATE TABLE order_items RESTART IDENTITY;";
    	SqlParameterSource param2 = new BeanPropertySqlParameterSource(item);
 		template.update(sqlOrderItem, param2);
    	
    	
    	MockHttpSession userSession = SessionUtil.userSession04();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm")
    			.session(userSession))
    			.andExpect(view().name("forward:/shoppingCart"))
    			.andReturn();
    	MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
    	System.out.println(session);
    }
    
    /**
     * 
     *
     */
    @Test
    @DatabaseSetup("classpath:order/orders_1.xlsx")
    @DisplayName("ログイン画面に遷移(状態：ログイン有り、カート有り)")
    void confirm_03() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession04();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm")
    			.session(userSession))
    			.andExpect(view().name("order_confirm.html"))
    			.andReturn();
    	MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
    	System.out.println(session);
    }
    
    /**
     * 
     *
     */
    @Test
    @DatabaseSetup("classpath:order/orders_1.xlsx")
    @DisplayName("未入力時エラー")
    void confirmRegister_01() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession05();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.session(userSession))
    			.andExpect(view().name("order_confirm.html"))
    			.andReturn();
    	ModelAndView mav = mvcResult.getModelAndView();
    	BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + "orderConfirmForm");
    	System.out.println(result);
    	assertAll("エラーメッセージの確認",
    			() -> assertEquals("名前を入力して下さい", result.getFieldError("destinationName").getDefaultMessage(), "name:エラーメッセージが不一致"),
    			() -> assertEquals("メールアドレスを入力して下さい", result.getFieldError("destinationEmail").getDefaultMessage(), "email:エラーメッセージが不一致"),
    			() -> assertEquals("住所を入力して下さい", result.getFieldError("destinationAddress").getDefaultMessage(), "address:エラーメッセージが不一致"),
    			() -> assertEquals("郵便番号を入力して下さい", result.getFieldError("destinationZipcode").getDefaultMessage(), "zipcode:エラーメッセージが不一致"),
    			() -> assertEquals("電話番号を入力して下さい", result.getFieldError("destinationTel").getDefaultMessage(), "tell:エラーメッセージが不一致"),
    			() -> assertEquals("配達日時を入力して下さい", result.getFieldError("deliveryDate").getDefaultMessage(), "date:エラーメッセージが不一致")
    			);
    }
    
    /**
     * 
     *
     */
    @Test
    @DisplayName("画面遷移")
    void confirmRegister_02() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession04();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.param("destinationName", "山田太郎")
                .param("destinationEmail", "yamada@example.com")
                .param("destinationZipcode", "111-1111")
                .param("destinationAddress", "東京都新宿区")
                .param("destinationTel", "080-0000-0000")
                .param("deliveryDate", "2100-01-01")
                .param("deliveryHour", "10")
                .param("paymentMethod", "1")
    			.session(userSession))
    			.andExpect(view().name("forward:/finished"))
    			.andReturn();
    }
    
    /**
     * 
     *
     */
    @Test
    @DatabaseSetup("classpath:order/orders_1.xlsx")
    @DisplayName("配達日が3時間より前")
    void confirmRegister_03() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession04();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.param("destinationName", "山田太郎")
    			.param("destinationEmail", "yamada@example.com")
    			.param("destinationZipcode", "111-1111")
    			.param("destinationAddress", "東京都新宿区")
    			.param("destinationTel", "080-0000-0000")
    			.param("deliveryDate", "2000-01-01")
    			.param("deliveryHour", "10")
    			.param("paymentMethod", "1")
    			.session(userSession))
    			.andExpect(view().name("order_confirm.html"))
    			.andReturn();
    	ModelAndView mav = mvcResult.getModelAndView();
    	BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + "orderConfirmForm");
    	System.out.println(result);
    	assertEquals("今から3時間後の日時をご入力ください", result.getFieldError("deliveryHour").getDefaultMessage(), "deliveryHour:エラーメッセージが不一致");
    }
    
    /**
     * 
     *
     */
    @Test
    @DisplayName("画面遷移(paymentMethod:2)")
    void confirmRegister_04() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession05();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.param("destinationName", "山田太郎")
                .param("destinationEmail", "yamada@example.com")
                .param("destinationZipcode", "111-1111")
                .param("destinationAddress", "東京都新宿区")
                .param("destinationTel", "080-0000-0000")
                .param("deliveryDate", "2100-01-01")
                .param("deliveryHour", "10")
                .param("paymentMethod", "2")
    			.session(userSession))
    			.andExpect(view().name("forward:/finished"))
    			.andReturn();
    }

}
