package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leonardo.document.UserDocs;

public interface UserRepository extends MongoRepository<UserDocs, String> {
	UserDocs findOneByUsernameAndStatus(String username,int status);
}
