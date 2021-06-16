package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.model.document.MenuDocs;

@Repository
public interface MenuRepository extends MongoRepository<MenuDocs, String> {

}
