package com.leonardo.document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TypeColor {
	
	@JsonProperty("color")
	private String color;
	
	@JsonProperty("imgColor")
	private String imgColor;
}
