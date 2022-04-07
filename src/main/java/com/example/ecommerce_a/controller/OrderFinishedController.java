package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/finished")
public class OrderFinishedController {
	@Autowired
	private HttpSession session;

	@RequestMapping("")
	public String index() {
		Integer userId = (Integer) session.getAttribute("userId");
		Integer finishedId = (Integer) session.getAttribute("finished");
		if (userId == null) {
			return "forward:/login";
		} else if (finishedId == null) {
			return "forward:/confirm";
		}
		session.removeAttribute("finished");
		return "order_finished";
	}
}
