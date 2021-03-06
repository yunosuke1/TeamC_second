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
        DependencyInjectionTestExecutionListener.class, // ???????????????????????????DI???????????????????????????
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetup???@ExpectedDatabase?????????????????????????????????
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
    @DisplayName("???????????????????????????(???????????????????????????)")
    void confirm_01() throws Exception {
        mockMvc.perform(get("/confirm")).andExpect(view().name("forward:/login"));
    }
    
    /**
     * 
     *
     */
    @Test
    @Sql("classpath:order/re.confirm_02.sql")
    @DisplayName("???????????????????????????(?????????????????????????????????????????????)")
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
    @DisplayName("???????????????????????????(?????????????????????????????????????????????)")
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
    @DisplayName("?????????????????????")
    void confirmRegister_01() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession05();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.session(userSession))
    			.andExpect(view().name("order_confirm.html"))
    			.andReturn();
    	ModelAndView mav = mvcResult.getModelAndView();
    	BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + "orderConfirmForm");
    	System.out.println(result);
    	assertAll("?????????????????????????????????",
    			() -> assertEquals("??????????????????????????????", result.getFieldError("destinationName").getDefaultMessage(), "name:????????????????????????????????????"),
    			() -> assertEquals("?????????????????????????????????????????????", result.getFieldError("destinationEmail").getDefaultMessage(), "email:????????????????????????????????????"),
    			() -> assertEquals("??????????????????????????????", result.getFieldError("destinationAddress").getDefaultMessage(), "address:????????????????????????????????????"),
    			() -> assertEquals("????????????????????????????????????", result.getFieldError("destinationZipcode").getDefaultMessage(), "zipcode:????????????????????????????????????"),
    			() -> assertEquals("????????????????????????????????????", result.getFieldError("destinationTel").getDefaultMessage(), "tell:????????????????????????????????????"),
    			() -> assertEquals("????????????????????????????????????", result.getFieldError("deliveryDate").getDefaultMessage(), "date:????????????????????????????????????")
    			);
    }
    
    /**
     * 
     *
     */
    @Test
    @DisplayName("????????????(paymentMethod:1)")
    void confirmRegister_02() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession04();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.param("destinationName", "????????????")
                .param("destinationEmail", "yamada@example.com")
                .param("destinationZipcode", "111-1111")
                .param("destinationAddress", "??????????????????")
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
    @DisplayName("????????????3???????????????")
    void confirmRegister_03() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession04();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.param("destinationName", "????????????")
    			.param("destinationEmail", "yamada@example.com")
    			.param("destinationZipcode", "111-1111")
    			.param("destinationAddress", "??????????????????")
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
    	assertEquals("?????????3??????????????????????????????????????????", result.getFieldError("deliveryHour").getDefaultMessage(), "deliveryHour:????????????????????????????????????");
    }
    
    /**
     * 
     *
     */
    @Test
    @DisplayName("????????????(paymentMethod:2)")
    void confirmRegister_04() throws Exception {
    	MockHttpSession userSession = SessionUtil.userSession05();
    	MvcResult mvcResult = mockMvc.perform(get("/confirm/register")
    			.param("destinationName", "????????????")
                .param("destinationEmail", "yamada@example.com")
                .param("destinationZipcode", "111-1111")
                .param("destinationAddress", "??????????????????")
                .param("destinationTel", "080-0000-0000")
                .param("deliveryDate", "2100-01-01")
                .param("deliveryHour", "10")
                .param("paymentMethod", "2")
    			.session(userSession))
    			.andExpect(view().name("forward:/finished"))
    			.andReturn();
    }

}
