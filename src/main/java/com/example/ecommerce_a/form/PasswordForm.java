package com.example.ecommerce_a.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.ecommerce_a.domain.model.ValidGroup1;

public class PasswordForm {

	@NotBlank(message="パスワードを入力して下さい",groups = ValidGroup1.class)
	@Size(min=8, max=16, message="パスワードは８文字以上１６文字以内で設定してください",groups = ValidGroup1.class)
	private String newPassword;
	
	@NotBlank(message="確認用パスワードを入力して下さい",groups = ValidGroup1.class)
	private String confirmpassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	
	
}
