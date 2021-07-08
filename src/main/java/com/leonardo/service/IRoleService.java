package com.leonardo.service;

import java.util.List;

import com.leonardo.document.RoleDocs;

public interface IRoleService {
	RoleDocs findByRoleName(String roleName);
}
