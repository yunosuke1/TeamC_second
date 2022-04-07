package com.example.ecommerce_a.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.domain.Review;
import com.example.domain.User;

public class SessionUtil {

	public static MockHttpSession createLoginUserSession() {
		User user = new User();
		user.setId(1);
		user.setName("山田太郎");
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("user", user);
		return createMockHttpSession(sessionMap);
	}

	public static MockHttpSession createRegistUserSession() {
		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setName("山田太郎");
		user.setEmail("yamada@example.com");
		user.setPassword("Abcd1234");
		user.setZipcode("111-1111");
		user.setAddress("東京都新宿区");
		user.setTelephone("080-0000-0001");
		userList.add(user);
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("register", userList);
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
