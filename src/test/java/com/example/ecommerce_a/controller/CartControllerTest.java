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
class CartControllerTest {
	
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
	@DisplayName("urlのリンク")
    void Link_url() throws Exception {
        // ①コントローラー呼出し
        mockMvc.perform(get("/shoppingCart/url"))
                .andExpect(view().name("cart_list.html"))
                .andReturn();
    }

    @Test
	@DisplayName("カートに商品がない時")
    void nonItem() throws Exception {
        // ①コントローラー呼出し
        mockMvc.perform(get("/shoppingCart"))
                .andExpect(view().name("cart_list.html"))
                .andReturn();
    }
    
    @Test
    @DisplayName("ordersに登録されたデータを正しく取得できているか(preIdがnull)")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx")
    void getOrderItemList_01() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/shoppingCart")
    			.sessionAttr("userId",1)
    			).andExpect(view().name("forward:/shoppingCart/url"))
    			.andReturn();
    	
    	 ModelAndView mav = mvcResult.getModelAndView();
         @SuppressWarnings(value = "unchecked")// 下のキャストのワーニングを出さないようにする
         List<OrderItem> orderItemList = (List<OrderItem>) mav.getModel().get("orderItemList");
         
         assertEquals("Gorgeous4サンド",orderItemList.get(0).getItem().getName());
         assertEquals("エスプレッソフラペチーノ",orderItemList.get(1).getItem().getName());
    }
    
    @Test
    @DisplayName("ordersに登録されたデータを正しく取得できているか(userdがnull)")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx")
    void getOrderItemList_02() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/shoppingCart")
    			.sessionAttr("preId",1)
    			).andExpect(view().name("forward:/shoppingCart/url"))
    			.andReturn();
    	
    	 ModelAndView mav = mvcResult.getModelAndView();
         @SuppressWarnings(value = "unchecked")// 下のキャストのワーニングを出さないようにする
         List<OrderItem> orderItemList = (List<OrderItem>) mav.getModel().get("orderItemList");

         assertEquals("Gorgeous4サンド",orderItemList.get(0).getItem().getName());
         assertEquals("エスプレッソフラペチーノ",orderItemList.get(1).getItem().getName());
    }
    
    @Test
    @DisplayName("ordersに登録されたデータを正しく取得できているか(userIdとpreIdをセット)")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx")
    void getOrderItemList_03() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/shoppingCart")
    			.sessionAttr("userId",1)
    			.sessionAttr("preId",1)
    			).andExpect(view().name("forward:/shoppingCart/url"))
    			.andReturn();
    }
    
    @Test
    @DisplayName("カートに商品がない場合")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx",type = DatabaseOperation.DELETE_ALL)
    void getOrderItemList_04() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/shoppingCart")
    			.sessionAttr("preId",1)
    			).andExpect(view().name("forward:/shoppingCart/url"))
    			.andReturn();
    	
    	 ModelAndView mav = mvcResult.getModelAndView();
         @SuppressWarnings(value = "unchecked")// 下のキャストのワーニングを出さないようにする
         String emptymessage = (String) mav.getModel().get("emptyMessage");

         assertEquals("カートに商品がありません",emptymessage);
    }
    
    @Test
    @DisplayName("カートからアイテムを削除出来ているか")
    @DatabaseSetup(value = "classpath:order/order_history.xlsx")
    @ExpectedDatabase(value = "classpath:order/cartServiceDeleteTest.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void deleteItem() throws Exception {
    	MvcResult mvcResult = mockMvc.perform(post("/shoppingCart/delete")
    			.param("deleteId", "1")
    			).andExpect(view().name("redirect:/shoppingCart"))
    			.andReturn();
    }
    
 }

    