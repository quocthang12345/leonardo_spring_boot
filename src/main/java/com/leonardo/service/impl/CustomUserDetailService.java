package com.leonardo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leonardo.document.UserDetailImpl;
import com.leonardo.document.UserDocs;
import com.leonardo.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDocs user = userRepo.findOneByUsernameAndStatus(username, 1);
		if(user != null) {
			UserDetails userDetails = new UserDetailImpl(user);
			return userDetails;
		}
		return null;
	} 

}
