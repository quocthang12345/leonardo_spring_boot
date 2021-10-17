package com.leonardo.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListMenuOfWomen {
	@Field("Shirt")
	private List<String> Shirts;
	
	@Field("Trousers")
	private List<String> Trousers;
	
	@Field("Bags")
	private List<String> Bag;
	
	@Field("Accessories")
	private List<String> Accessories;
}
