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
import org.springframework.web.servlet.ModelAndView;

import com.example.ecommerce_a.domain.Item;
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
   @DisplayName("商品一覧へ遷移")
   @Test
   void shoppingListTest_01() throws Exception {
   			mockMvc.perform(get("/shoppingList")).andExpect(view().name("item_list_coffee"));
   }
   
   /**
   *
   */
  @DisplayName("商品一覧の並び替え(安い)")
  @Test
  void orderByTest_01() throws Exception {
  	
  	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/orderBy")
			.param("select", "low")
  			)
  			.andExpect(view().name("item_list_coffee"))
  			.andReturn()
              ;
  	ModelAndView mav = mvcResult.getModelAndView();
  	@SuppressWarnings("unchecked")
	List<Item> list = (List<Item>) mav.getModel().get("itemList");
  	assertAll("並び順の確認",
			() -> assertEquals("チョコクッキー", list.get(0).getName(), "一番安い順番が不一致"),
			() -> assertEquals("エスプレッソフラペチーノ", list.get(17).getName(), "不一致")
			);
  }
  
  /**
  *
  */
 @DisplayName("商品一覧の並び替え(高い)")
 @Test
 void orderByTest_02() throws Exception {
 	
 	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/orderBy")
			.param("select", "high")
 			)
 			.andExpect(view().name("item_list_coffee"))
 			.andReturn()
             ;
 	ModelAndView mav = mvcResult.getModelAndView();
  	@SuppressWarnings("unchecked")
	List<Item> list = (List<Item>) mav.getModel().get("itemList");
  	assertAll("並び順の確認",
  			() -> assertEquals("エスプレッソフラペチーノ", list.get(0).getName(), "高い順番が不一致"),
			() -> assertEquals("チョコクッキー", list.get(17).getName(), "不一致")
			);
 }
 
 /**
  *
  */
 @DisplayName("商品検索(アクションなし)")
 @Test
 void searchWordTest_01() throws Exception {

	 mockMvc.perform(get("/shoppingList/searchWord"))
	 .andExpect(view().name("forward:/shoppingList"))
	 ;

 }
 
 /**
  *検索結果が無い事を確認
  */
 @DisplayName("商品検索(値無し)")
 @Test
 void searchWordTest_02() throws Exception {
	 
 	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "")
			 )
	 .andExpect(view().name("forward:/shoppingList")).andReturn()
	 ;
 	ModelAndView mav = mvcResult.getModelAndView();
 	@SuppressWarnings("unchecked")
	List<Item> list = (List<Item>) mav.getModel().get("itemList");
 	assertNull(list);
 }
 
 /**
  *
  */
 @DisplayName("商品一覧の並び替え(安い順、該当商品なし)")
 @Test
 void searchWordTest_03() throws Exception {
	 
 	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "low")
			 )
	 .andExpect(view().name("item_list_coffee")).andReturn()
	 ;
 	ModelAndView mav = mvcResult.getModelAndView();
  	assertEquals("該当する商品がありません", mav.getModel().get("nullMessage"), "メッセージが不一致");
 }
 
 /**
  * 
  */
 @DisplayName("商品一覧の並び替え(安い順、コーヒーで検索)")
 @Test
 void searchWordTest_04() throws Exception {
	 
 	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "low")
			 .param("searchWord", "コーヒー")
			 )
	 .andExpect(view().name("item_list_coffee")).andReturn()
	 ;
 	ModelAndView mav = mvcResult.getModelAndView();
  	@SuppressWarnings("unchecked")
	List<Item> list = (List<Item>) mav.getModel().get("itemList");
  	System.out.println(list);
  	assertAll("並び順の確認",
  			() -> assertEquals("ドリップコーヒー", list.get(0).getName(), "安い順で検索が不一致"),
			() -> assertEquals("アイスコーヒー", list.get(1).getName(), "不一致")
			);
 }
 /**
  *
  */
 @DisplayName("商品一覧の並び替え(高い順、該当する商品なし)")
 @Test
 void searchWordTest_05() throws Exception {
	 
 	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "high")
			 )
	 .andExpect(view().name("item_list_coffee")).andReturn()
	 ;
 	ModelAndView mav = mvcResult.getModelAndView();
  	assertEquals("該当する商品がありません", mav.getModel().get("nullMessage"), "メッセージが不一致");
 }
 /**
  *
  */
 @DisplayName("商品一覧の並び替え(高い順、コーヒーで検索)")
 @Test
 void searchWordTest_06() throws Exception {
	 
 	MvcResult mvcResult = mockMvc.perform(get("/shoppingList/searchWord")
			 .param("select", "high")
			 .param("searchWord", "コーヒー")
			 )
	 .andExpect(view().name("item_list_coffee")).andReturn()
	 ;
	 ModelAndView mav = mvcResult.getModelAndView();
	  	@SuppressWarnings("unchecked")
		List<Item> list = (List<Item>) mav.getModel().get("itemList");
	  	System.out.println(list);
	  	assertAll("並び順の確認",
	  			() -> assertEquals("アイスコーヒー", list.get(0).getName(), "高い順で検索が不一致"),
	  			() -> assertEquals("ドリップコーヒー", list.get(1).getName(), "不一致")
				);
	 
 }
 
 

}
