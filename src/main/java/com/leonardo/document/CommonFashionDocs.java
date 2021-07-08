package com.leonardo.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "fashion")
public class CommonFashionDocs extends Common {
	
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
	
	@Field(value = "model")
	private String model;
	
	@Field(value = "price")
	private String price;
	
	@Field(value = "typeColor")
	private List<TypeColor> typeColor = new ArrayList<TypeColor>();
	
	@Field(value = "listImg")
	private List<String> listImg = new ArrayList<String>();
	
	
	
}
