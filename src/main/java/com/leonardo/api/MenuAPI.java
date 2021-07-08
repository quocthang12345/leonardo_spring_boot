package com.leonardo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.document.MenuDocs;
import com.leonardo.service.IMenuService;

@RestController
@RequestMapping("/api")
public class MenuAPI {
	
	@Autowired
	private IMenuService menuService;
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/getMenu"}
	)
	public List<MenuDocs> findListValue(){
		return menuService.findAll();
	}
}
