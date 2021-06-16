package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.model.document.BagsDocs;

@Repository
public interface BagsRepository extends MongoRepository<BagsDocs, String> {

}
