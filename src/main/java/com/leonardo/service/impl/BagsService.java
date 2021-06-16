package com.leonardo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonardo.model.document.BagsDocs;
import com.leonardo.model.document.CommonFashionDocs;
import com.leonardo.repository.BagsRepository;
import com.leonardo.service.IBagsService;

@Service
public class BagsService implements IBagsService {

	@Autowired
	private BagsRepository bagsRepo;
	
	@Override
	public List<BagsDocs> findAll() {
		return bagsRepo.findAll();
	}
	
}
