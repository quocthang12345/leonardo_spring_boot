package com.leonardo.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.leonardo.document.UserDocs;
import com.leonardo.repository.UserRepository;
import com.leonardo.service.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public boolean RegisterUser(UserDocs user) {
		if(user.getUsername() != null) {
			UserDocs checkUser = userRepo.findOneByUsernameAndStatus(user.getUsername(), user.getStatus());
			if(checkUser == null) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				userRepo.save(user);
				return true;
			}
		}
		return false;
	}

	@Override
	public UserDocs findByUsernameAndStatus(String username, int status) {
		return userRepo.findOneByUsernameAndStatus(username, status);
	}

	@Override
	public UserDocs findById(String id) {
		return userRepo.findById(id).get();
	}
	
	@Override
	public UserDocs findOneByVerifyCode(String verifyCode) {
		return userRepo.findOneByVerifyCode(verifyCode);
	}
	
	@Override
	public void updateUser(UserDocs user) {
		Optional.ofNullable(user).map(item -> userRepo.save(item)).orElseThrow();
	}
}
