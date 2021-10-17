package com.leonardo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.document.ListJourneyDocs;
import com.leonardo.service.IListJourneyService;

@RestController
@RequestMapping("/api")
public class listJourneyAPI {
	
	@Autowired
	private IListJourneyService journeyService;
	
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			},
			path = {"/getListJourney"}
	)
	public List<ListJourneyDocs> findValue(){
		return journeyService.findValue();
	}
}
