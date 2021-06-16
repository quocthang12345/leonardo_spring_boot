package com.leonardo.model.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "cart")
@Data
public class CartDocs {
	
	@Field(value = "listItemInCart")
	private List<CommonFashionDocs> listItemInCart = new ArrayList<CommonFashionDocs>();
}
