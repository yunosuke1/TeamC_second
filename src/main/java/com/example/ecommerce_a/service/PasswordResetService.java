package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce_a.domain.PasswordReset;
import com.example.ecommerce_a.repository.PasswordResetRepository;

@Service
@Transactional
public class PasswordResetService {

	@Autowired
	private PasswordResetRepository repository;

	public void insert(PasswordReset reset) {
		repository.insert(reset);
	}
}
