package com.leonardo.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Document(collection = "cart")
@AllArgsConstructor
@Data
public class CartDocs extends Common {

	
	@Field("ItemNameInCart")
	private final String anotherName;
	
	@Field("count")
	private final long count;
	
	@Field("type")
	private final String type;
	
}


