package com.leonardo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.model.dto.FashionCommonDTO;

@Component
public class FashionCommonConvert<T> {
	@Autowired
	private ModelMapper modelMapper;
	
	public FashionCommonDTO toDTO(T FashionDocs) {
		FashionCommonDTO fashion = modelMapper.map(FashionDocs,FashionCommonDTO.class);
		return fashion;
	}
	
//	public T toDocs(FashionCommonDTO cartDto) {
//		T course = modelMapper.map(cartDto);
//	    return (T) course;
//	}
}
