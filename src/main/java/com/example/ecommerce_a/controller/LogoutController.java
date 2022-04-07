package com.example.ecommerce_a.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	@Autowired
	private HttpSession session;

	@RequestMapping("")
	public String logout() {
		session.invalidate();
		return "redirect:/login";
	}
	
}
