package com.leonardo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.model.document.BagsDocs;
import com.leonardo.service.IBagsService;

@RestController
@RequestMapping("/api")
public class BagsAPI {
	
	@Autowired
	private IBagsService bagsService;
	
	@GetMapping(path = "/get")
	public List<BagsDocs> findAll(){
		List<BagsDocs> result = bagsService.findAll();
		return result;
	}
}
