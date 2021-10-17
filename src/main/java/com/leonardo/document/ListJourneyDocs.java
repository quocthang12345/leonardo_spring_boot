package com.leonardo.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "listJourney")
@Data
public class ListJourneyDocs extends Common{
	
	@Field(value = "title")
	private String title;
	
	@Field(value = "img")
	private String imgJourney;
	
	@Field(value = "description")
	private List<String> descriptionJourney = new ArrayList<String>();
	
}
