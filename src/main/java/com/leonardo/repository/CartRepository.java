package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.model.document.CartDocs;

@Repository
public interface CartRepository extends MongoRepository<CartDocs, String> {

}
