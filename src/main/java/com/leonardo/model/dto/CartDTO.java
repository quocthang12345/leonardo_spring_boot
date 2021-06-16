package com.leonardo.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
	private List<FashionCommonDTO> listItemInCart;
}
