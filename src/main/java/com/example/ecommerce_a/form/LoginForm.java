package com.example.ecommerce_a.form;

/**
 * ログイン時に使用するフォームです。
 * 
 * @author kashimamiyu
 *
 */
public class LoginForm {

	/**メールアドレス*/
	private String email;
	/**パスワード*/
	private String password;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
	}
	
}
