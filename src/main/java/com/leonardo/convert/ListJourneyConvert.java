package com.leonardo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.model.document.ListJourneyDocs;
import com.leonardo.model.dto.ListJourneyDTO;

@Component
public class ListJourneyConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ListJourneyDTO toDTO(ListJourneyDocs listJourneyDocs) {
		ListJourneyDTO journey = modelMapper.map(listJourneyDocs,ListJourneyDTO.class);
		return journey;
	}
	
	public ListJourneyDocs toDocs(ListJourneyDTO listJourneyDto) {
		ListJourneyDocs journey = modelMapper.map(listJourneyDto, ListJourneyDocs.class);
	    return journey;
	}
}
