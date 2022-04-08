package com.example.ecommerce_a.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.ecommerce_a.domain.User;


public class SessionUtil {

	public static MockHttpSession createLoginUserSession() {
		User user = new User();
		user.setId(1);
		user.setName("山田太郎");
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("user", user);
		return createMockHttpSession(sessionMap);
	}

	public static MockHttpSession userSession01() {
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("morimori");
		user.setZipcode("1111111");
		user.setAddress("テスト住所");
		user.setTelephone("テスト電話番号");
		userList.add(user);
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("user", userList);
		return createMockHttpSession(sessionMap);
	}
	public static MockHttpSession userSession02() {
		Integer userId = 1;
		
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setName("テストユーザ");
		user.setEmail("coffeeshop.test@gmail.com");
		user.setPassword("morimori");
		user.setZipcode("1111111");
		user.setAddress("テスト住所");
		user.setTelephone("テスト電話番号");
		userList.add(user);
		
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("userId", userId);
		sessionMap.put("user", userList);
		return createMockHttpSession(sessionMap);
	}
	public static MockHttpSession userSession03() {
		Integer userId = 1;
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("userId", userId);
		return createMockHttpSession(sessionMap);
	}
	
	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
		}
		return mockHttpSession;
	}

}
