package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.document.ListJourneyDocs;

@Repository
public interface listJourneyRepository extends MongoRepository<ListJourneyDocs, String>{

}
