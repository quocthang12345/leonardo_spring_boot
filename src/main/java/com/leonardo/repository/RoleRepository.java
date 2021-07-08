package com.leonardo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.leonardo.document.RoleDocs;

public interface RoleRepository extends MongoRepository<RoleDocs, String> {

	RoleDocs findByRoleName(String roleName);
}
