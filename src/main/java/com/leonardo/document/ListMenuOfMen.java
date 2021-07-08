package com.leonardo.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListMenuOfMen {
	@JsonProperty("Card")
	private List<String> wallets;
	
	@JsonProperty("Shoes")
	private List<String> shoes;
	
	@JsonProperty("Bags")
	private List<String> bags;
	
	@JsonProperty("Accessories")
	private List<String> accessories;
}
