package com.leonardo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.document.CommonFashionDocs;

@Repository
public interface FashionRepository extends MongoRepository<CommonFashionDocs, String> {

	List<CommonFashionDocs> findByType(String type);
	List<CommonFashionDocs> findByTypeAndModel(String type, String model);
	CommonFashionDocs findOneByAnotherName(String anotherName);
	Boolean deleteByAnotherName(String anotherName);
}
