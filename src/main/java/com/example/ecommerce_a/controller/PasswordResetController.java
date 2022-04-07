package com.example.ecommerce_a.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.PasswordReset;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.domain.model.GroupOrder;
import com.example.ecommerce_a.form.PasswordForm;
import com.example.ecommerce_a.service.PasswordResetService;
import com.example.ecommerce_a.service.UserService;

@Controller
@RequestMapping("/reset")
public class PasswordResetController {
	@RequestMapping("")
	public String index() {
		return "password_reset";
	}

	@Autowired
	private MailSender mailSender;

	@Autowired
	private PasswordResetService service;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/resetConfirm")
	public String confirmPasswordReset(String userEmail, Model model) {
		User user = userService.findByEmail(userEmail);
		if (user == null) {
			model.addAttribute("errorMessage", "アカウントが存在しません");
			return index();
		} else if (user != null) {
			session.setAttribute("userEmailPass", userEmail);
		}
		String uniqueUrl = UUID.randomUUID().toString();

		SimpleMailMessage msg = new SimpleMailMessage();
		try {
			msg.setFrom("coffeeShopMaster2022@mail.com");
			msg.setTo(userEmail);
			msg.setSubject("パスワード変更URLの送付");
			msg.setText("http://localhost:8080/coffeeShop/reset/passwordReset?key=" + uniqueUrl);

			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		}
		return "email_submit";
	}

	@RequestMapping("/passwordReset")
	public String index2(String key) {
		session.setAttribute("uniqueUrl", key);
		return "reset_password";
	}
    @ModelAttribute
    private PasswordForm setUpPasswordForm() {
		return new PasswordForm();
	}
	@RequestMapping("/passwordResetFinished")
	public String registerRePassword(@Validated(GroupOrder.class) PasswordForm form,BindingResult result) {
		System.out.println(form.getNewPassword());
		System.out.println(form.getConfirmpassword());
		if (!form.getNewPassword().equals(form.getConfirmpassword())){
			result.rejectValue("confirmpassword", "", "パスワードが一致していません");
		}
		
		if (result.hasErrors()) {
			return "reset_password";
		}
	
		PasswordReset reset = new PasswordReset();
		String userEmail = (String) session.getAttribute("userEmailPass");
		reset.setUserEmail(userEmail);
		reset.setUniqueUrl((String) session.getAttribute("uniqueUrl"));
		service.insert(reset);
		userService.updatePassword(userEmail, form.getNewPassword());
		
		return "reset_finished";
	}

}
