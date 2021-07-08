package com.leonardo.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListMenuOfWomen {
	@JsonProperty("Shirts")
	private List<String> shirts;
	
	@JsonProperty("Trousers")
	private List<String> trousers;
	
	@JsonProperty("Bags")
	private List<String> bags;
	
	@JsonProperty("Accessories")
	private List<String> accessories;
}
