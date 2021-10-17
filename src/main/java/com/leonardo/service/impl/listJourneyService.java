package com.leonardo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonardo.document.ListJourneyDocs;
import com.leonardo.repository.listJourneyRepository;
import com.leonardo.service.IListJourneyService;

@Service
public class listJourneyService implements IListJourneyService{

	@Autowired
	private listJourneyRepository listJourneyRepo;
	
	@Override
	public List<ListJourneyDocs> findValue() {
		return listJourneyRepo.findAll();
	}

}
