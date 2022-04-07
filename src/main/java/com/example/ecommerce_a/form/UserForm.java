package com.example.ecommerce_a.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.ecommerce_a.domain.model.ValidGroup1;
import com.example.ecommerce_a.domain.model.ValidGroup2;

/**
 * ユーザー登録時に使用するフォーム
 * 
 * @author kashimamiyu
 *
 */
public class UserForm {

	/**名前*/
	@NotBlank(message="名前を入力して下さい",groups = ValidGroup1.class)
	private String name;
	/**メールアドレス*/
	@NotBlank(message="メールアドレスを入力して下さい",groups = ValidGroup1.class)
	@Email(message="メールアドレスの形式が不正です", groups = ValidGroup2.class)
	private String email;
	/**郵便番号*/
	@NotBlank(message="郵便番号を入力して下さい",groups = ValidGroup1.class)
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$", message="郵便番号はXXX-XXXXの形式で入力してください", groups = ValidGroup2.class)
	private String zipcode;
	/**住所*/
	@NotBlank(message="住所を入力して下さい",groups = ValidGroup1.class)
	private String address;
	/**電話番号*/
	@NotBlank(message="電話番号を入力して下さい",groups = ValidGroup1.class)
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message="電話番号はXXX-XXXX-XXXXの形式で入力してください",groups = ValidGroup2.class)
	private String telephone;
	/**パスワード*/
	@NotBlank(message="パスワードを入力して下さい",groups = ValidGroup1.class)
	@Size(min=8, max=16, message="パスワードは８文字以上１６文字以内で設定してください",groups = ValidGroup1.class)
	private String password;
	/**確認用パスワード*/
	@NotBlank(message="確認用パスワードを入力して下さい",groups = ValidGroup1.class)
	private String confirmpassword;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}
	
	
	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	
}
