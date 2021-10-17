package com.leonardo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.leonardo.document.UserDocs;

public interface UserRepository extends MongoRepository<UserDocs, String> {
	UserDocs findOneByUsernameAndStatus(String username,int status);

	Optional<UserDocs> findByEmail(String email);
	
	UserDocs findOneByVerifyCode(String verifyCode);
	
}
