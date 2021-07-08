package com.leonardo.service.impl;

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

}
