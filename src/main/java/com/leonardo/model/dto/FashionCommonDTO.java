package com.leonardo.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FashionCommonDTO {
	
	private String _id;
	
	private Long id;
	
	private String imgDisplay;
	
	private String imgContinue;
	
	private String name;
	
	private String anotherName;
	
	private String type;
	
	private String price;
	
	private String color;
	
	private String imgColor;
	
	private List<String> listImg = new ArrayList<String>();
	
}
