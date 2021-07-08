package com.leonardo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.document.CommonFashionDocs;
import com.leonardo.document.dto.CommonFashionDTO;

@Component
public class CommonFashionConvert {
	@Autowired
	private ModelMapper modelMapper;
	
	public CommonFashionDTO toDTO(CommonFashionDocs commonFashionDocs) {
		CommonFashionDTO commonFashion = modelMapper.map(commonFashionDocs,CommonFashionDTO.class);
		return commonFashion;
	}
	
	public CommonFashionDocs toDocs(CommonFashionDTO commnonFashionDto) {
		CommonFashionDocs commonFashion = modelMapper.map(commnonFashionDto, CommonFashionDocs.class);
	    return commonFashion;
	}
}
