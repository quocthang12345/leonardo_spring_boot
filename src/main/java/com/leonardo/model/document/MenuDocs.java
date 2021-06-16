package com.leonardo.model.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "menu")
@Data
public class MenuDocs extends Common {
	
	@Field(value = "listMenuOfMen")
	private ListMenuOfMen listMenuOfMen;
	
	@Field(value = "listMenuOfWoMen")
	private ListMenuOfWomen listMenuOfWoMen;
}
