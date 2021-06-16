package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.model.document.AccessoriesDocs;

@Repository
public interface AccessoriesRepository extends MongoRepository<AccessoriesDocs, String>{

}
