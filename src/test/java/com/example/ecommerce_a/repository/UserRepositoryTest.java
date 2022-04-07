package com.example.ecommerce_a.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.ecommerce_a.domain.User;
@SpringBootTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<User> USER_ROW_MAPPER  = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};
	
	@Test
	void testInsert() {
		User user = new User();
		user.setAddress("テスト");
		user.setEmail("abcccabcd234faaa@gmail.com");
		user.setName("齊藤");
		user.setTelephone("000-0900-1111");
		user.setZipcode("444-1111");
		user.setPassword("a4441111");
		repository.insert(user);
		String sql = "SELECT id,name,email,zipcode,address,telephone,password from users WHERE email = :email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", user.getEmail());
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		User user2 = userList.get(0);
		System.out.println(user2.getAddress());
		assertEquals(user.getAddress(),user2.getAddress(),"違います");
		
	}

	@Test
	void testFindByEmail() {
		User user = repository.findByEmail("yamada@sample.com");	
		assertEquals("山田太郎",user.getName(),"違います");
	}

	@Test
	void testFindByEmailAndPassword() {
		User user = repository.findByEmailAndPassword("testtaro@sample.co.jp","tarotarotaro");	
		assertEquals("テスト太郎",user.getName(),"違います");
	}

}
