package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

/**
 * ユーザー情報を操作するサービス
 * 
 * @author kashimamiyu
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * ユーザー情報を登録します
	 * 
	 * @param user
	 */
	public void insert(User user) {
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得します。
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * @param email　メールアドレス
	 * @param password パスワード
	 * @return　ユーザー情報
	 */
	public User login(String email, String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		return user;
	}
	
	public void updatePassword(String email, String password) {
		userRepository.updatePassword(email, password);
	}
}