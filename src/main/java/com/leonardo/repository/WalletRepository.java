package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.model.document.WalletDocs;

@Repository
public interface WalletRepository extends MongoRepository<WalletDocs, String> {

}
