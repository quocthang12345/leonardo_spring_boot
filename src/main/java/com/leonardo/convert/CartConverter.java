package com.leonardo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.model.document.CartDocs;
import com.leonardo.model.dto.CartDTO;

@Component
public class CartConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public CartDTO toDTO(CartDocs cartDocs) {
		CartDTO cart = modelMapper.map(cartDocs,CartDTO.class);
		return cart;
	}
	
	public CartDocs toDocs(CartDTO cartDto) {
		CartDocs course = modelMapper.map(cartDto, CartDocs.class);
	    return course;
	}
}
