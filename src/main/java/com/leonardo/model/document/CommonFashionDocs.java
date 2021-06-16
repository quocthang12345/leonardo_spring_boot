package com.leonardo.model.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class CommonFashionDocs extends Common {
	@Field(value = "id")
	private Long id;
	
	@Field(value = "imgDisplay")
	private String imgDisplay;
	
	@Field(value = "imgContinue")
	private String imgContinue;
	
	@Field(value = "name")
	private String name;
	
	@Field(value = "anotherName")
	private String anotherName;
	
	@Field(value = "type")
	private String type;
	
	@Field(value = "price")
	private String price;
	
	@Field(value = "typeColor")
	private List<TypeColor> typeColor = new ArrayList<TypeColor>();
	
	@Field(value = "listImg")
	private List<String> listImg = new ArrayList<String>();
	
	
	
}
