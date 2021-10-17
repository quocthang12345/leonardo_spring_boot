package com.leonardo.service;

import com.leonardo.document.UserDocs;

public interface IUserService {
	boolean RegisterUser(UserDocs user);
	
	UserDocs findByUsernameAndStatus(String username, int status);
	
	UserDocs findById(String id);
	
	UserDocs findOneByVerifyCode(String verifyCode);
	
	void updateUser(UserDocs user);
}
