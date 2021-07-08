package com.leonardo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonardo.document.MenuDocs;
import com.leonardo.repository.MenuRepository;
import com.leonardo.service.IMenuService;

@Service
public class MenuService implements IMenuService {

	@Autowired
	private MenuRepository menuRepo;
	
	
	@Override
	public List<MenuDocs> findAll() {
		return menuRepo.findAll();
	}

}
