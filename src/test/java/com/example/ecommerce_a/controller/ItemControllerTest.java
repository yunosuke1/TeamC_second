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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.ecommerce_a.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
        TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
class ItemControllerTest {
	
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
    * ログインしていない状態では、sessionのみにオーダーを格納する
    * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
    * 
    */
//   @DatabaseSetup("classpath:cart/items.xlsx")
   @DisplayName("商品一覧へ遷移")
   @Test
   void shoppingListTest_01() throws Exception {
   	
//   	MvcResult mvcResult = 
   			mockMvc.perform(get("/shoppingList")
   			)
   			.andExpect(view().name("item_list_coffee"))
//   			.andReturn()
               ;
//   	MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
//   	@SuppressWarnings(value = "unchecked")
//   	List<OrderItem> orderItemList = (List<OrderItem>)session.getAttribute("orderItemList");
//   	assertEquals("とんこつラーメン", orderItemList.get(0).getItem().getName(), "とんこつラーメンなし");
   }
   
   /**
   *
   *
   * ログインしていない状態では、sessionのみにオーダーを格納する
   * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
   * 
   */
//  @DatabaseSetup("classpath:cart/items.xlsx")
  @DisplayName("商品一覧の並び替え(安い)")
  
  @Test
  void orderByTest_01() throws Exception {
  	
//  	MvcResult mvcResult = 
  			mockMvc.perform(get("/shoppingList/orderBy")
			.param("select", "low")
  			)
  			.andExpect(view().name("item_list_coffee"))
//  			.andReturn()
              ;
//  	MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
//  	@SuppressWarnings(value = "unchecked")
//  	List<OrderItem> orderItemList = (List<OrderItem>)session.getAttribute("orderItemList");
//  	assertEquals("とんこつラーメン", orderItemList.get(0).getItem().getName(), "とんこつラーメンなし");
  }
  
  /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
// @DatabaseSetup("classpath:cart/items.xlsx")
 @DisplayName("商品一覧の並び替え(高い)")
 
 @Test
 void orderByTest_02() throws Exception {
 	
// 	MvcResult mvcResult = 
 			mockMvc.perform(get("/shoppingList/orderBy")
			.param("select", "high")
 			)
 			.andExpect(view().name("item_list_coffee"))
// 			.andReturn()
             ;
// 	MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
// 	@SuppressWarnings(value = "unchecked")
// 	List<OrderItem> orderItemList = (List<OrderItem>)session.getAttribute("orderItemList");
// 	assertEquals("とんこつラーメン", orderItemList.get(0).getItem().getName(), "とんこつラーメンなし");
 }
 
 /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void searchWordTest_01() throws Exception {
	 
// 	MvcResult mvcResult = 
	 mockMvc.perform(get("/shoppingList/searchWord")
			 )
	 .andExpect(view().name("forward:/shoppingList"))
	 ;

 }
 
 /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void searchWordTest_02() throws Exception {
	 
// 	MvcResult mvcResult = 
	 mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "")
			 )
	 .andExpect(view().name("forward:/shoppingList"))
	 ;
	 
 }
 
 /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void searchWordTest_03() throws Exception {
	 
// 	MvcResult mvcResult = 
	 mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "low")
			 )
	 .andExpect(view().name("item_list_coffee"))
	 ;
	 
 }
 /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void searchWordTest_04() throws Exception {
	 
// 	MvcResult mvcResult = 
	 mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "low")
			 .param("searchWord", "コーヒー")
			 )
	 .andExpect(view().name("item_list_coffee"))
	 ;
	 
 }
 /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void searchWordTest_05() throws Exception {
	 
// 	MvcResult mvcResult = 
	 mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "high")
			 )
	 .andExpect(view().name("item_list_coffee"))
	 ;
	 
 }
 /**
  *
  *
  * ログインしていない状態では、sessionのみにオーダーを格納する
  * そのため戻り値からsessionを取り出し、入力した値がただしくセッションに保存されていることを確認
  * 
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void searchWordTest_06() throws Exception {
	 
// 	MvcResult mvcResult = 
	 mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "high")
			 .param("searchWord", "コーヒー")
			 )
	 .andExpect(view().name("item_list_coffee"))
	 ;
	 
 }
 
 

}
