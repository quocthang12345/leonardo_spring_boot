package com.leonardo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.document.CartDocs;

@Repository
public interface CartRepository extends MongoRepository<CartDocs, String> {

	List<CartDocs> findByAnotherName(String anotherName);
	
}
