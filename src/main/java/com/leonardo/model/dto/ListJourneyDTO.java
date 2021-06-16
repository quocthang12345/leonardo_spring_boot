package com.leonardo.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListJourneyDTO {

	private String title;
	

	private String imgJourney;
	
	private List<String> descriptionJouney = new ArrayList<String>();
}
