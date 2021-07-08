package com.leonardo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonardo.document.RoleDocs;
import com.leonardo.repository.RoleRepository;
import com.leonardo.service.IRoleService;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public RoleDocs findByRoleName(String roleName) {
		return Optional.ofNullable(roleName).map(role -> roleRepo.findByRoleName(role)).orElse(null);
	}

}
