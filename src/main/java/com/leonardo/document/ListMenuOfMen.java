package com.leonardo.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListMenuOfMen {
	@Field("Card")
	private List<String> Wallet;
	
	@Field("Shoes")
	private List<String> Shoes;
	
	@Field("Bag")
	private List<String> Bag;
	
	@Field("Accessories")
	private List<String> Accessories;
}
