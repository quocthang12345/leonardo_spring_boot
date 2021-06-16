package com.leonardo.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuDTO {

	private List<List<String>> listMenuOfMen;
	
	private List<List<String>> listMenuOfWomen;
}
