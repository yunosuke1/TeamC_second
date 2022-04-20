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

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
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
class UserControllerTest {
	
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
     */
    @Test
    @DisplayName("ログイン画面に遷移(状態：ログイン無し)")
    void register_01() throws Exception {
        mockMvc.perform(post("/register")).andExpect(view().name("register_user"))
                ;
    }
    
    /**
     *
     */
    @Test
    @DisplayName("商品一覧画面に遷移(状態：ログイン有り)")
    void register_02() throws Exception {
    	MockHttpSession userSession = com.example.ecommerce_a.util.SessionUtil.userSession02();
    	mockMvc.perform(get("/register").session(userSession)).andExpect(view().name("redirect:/shoppingList"));
    }
    
    /**
     *
     */
    @Test
    @ExpectedDatabase(value = "classpath:user/users_3.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DisplayName("正常にユーザーを登録")
    void insert_01() throws Exception {
    	User users = new User();
		String sqluser = "TRUNCATE TABLE users RESTART IDENTITY;"
				+ "insert into users(name, email, password, zipcode, address, telephone) values('テストユーザ', 'coffeeshop.test@gmail.com', '$2a$10$Utoo6nr3XIFEh4xOZ9Zr1.n/PtEYBb8HhlLDDklaJwsj.T3uux4kq','1111111', 'テスト住所', 'テスト電話番号');";
		SqlParameterSource paramsuser = new BeanPropertySqlParameterSource(users);
		template.update(sqluser, paramsuser);
    	
    	mockMvc.perform(post("/register/insert")
    			.param("name", "山田太郎")
                .param("email", "yamada@example.com")
                .param("password", "Abcd1234")
                .param("confirmpassword", "Abcd1234")
                .param("zipcode", "111-1111")
                .param("address", "東京都新宿区")
                .param("telephone", "080-0000-0000")
                ).andExpect(view().name("redirect:/login"))
    			;
    }
    
    /**
     *
     */
    @Test
    @DisplayName("メールアドレス重複")
    void insert_02() throws Exception {
    	
    	MvcResult mvcResult = mockMvc.perform(post("/register/insert")
    			.param("name", "山田太郎")
    			.param("email", "coffeeshop.test@gmail.com")
    			.param("password", "Abcd1234")
    			.param("confirmpassword", "Abcd1234")
    			.param("zipcode", "111-1111")
    			.param("address", "東京都新宿区")
    			.param("telephone", "080-0000-0000")
    			).andExpect(view().name("register_user.html"))
    			.andReturn()
    			;
    	ModelAndView mav = mvcResult.getModelAndView();
    	BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + "userForm");
    	assertEquals("このメールアドレスは既に登録されています", result.getFieldError("email").getDefaultMessage(), "エラーメッセージが不一致");
    }
    
    /**
     *
     */
    @Test
    @DisplayName("パスワードが不一致")
    void insert_03() throws Exception {
    	
    	MvcResult mvcResult = mockMvc.perform(post("/register/insert")
    			.param("name", "山田太郎")
    			.param("email", "yamada@example.com")
    			.param("password", "Abcd1234")
    			.param("confirmpassword", "Acd1234")
    			.param("zipcode", "111-1111")
    			.param("address", "東京都新宿区")
    			.param("telephone", "080-0000-0000")
    			).andExpect(view().name("register_user.html"))
    			.andReturn()
    			;
    	ModelAndView mav = mvcResult.getModelAndView();
    	BindingResult result = (BindingResult) mav.getModel().get(BindingResult.MODEL_KEY_PREFIX + "userForm");
    	assertEquals("パスワードが一致していません", result.getFieldError("confirmpassword").getDefaultMessage(), "エラーメッセージが不一致");
    }

}
