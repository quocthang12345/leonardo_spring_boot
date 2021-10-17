package com.leonardo.convert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.document.CommonFashionDocs;
import com.leonardo.document.TypeColor;
import com.leonardo.document.dto.CommonFashionDTO;
import com.leonardo.repository.FashionRepository;

@Component
public class CommonFashionConvert {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FashionRepository fashionRepo;
	
	public CommonFashionDTO toDTO(CommonFashionDocs commonFashionDocs) {
		CommonFashionDTO commonFashion = modelMapper.map(commonFashionDocs,CommonFashionDTO.class);
		return commonFashion;
	}
	
	public CommonFashionDocs toDocs(CommonFashionDTO commonFashionDto) {
		if(commonFashionDto.get_id() == null) {
			CommonFashionDocs commonFashionForInsert = modelMapper.map(commonFashionDto, CommonFashionDocs.class);
			return commonFashionForInsert;
		}
		CommonFashionDocs commonFashionForUpdate = fashionRepo.findById(commonFashionDto.get_id()).get();
		commonFashionForUpdate.setAnotherName(commonFashionDto.getAnotherName());
		commonFashionForUpdate.setImgContinue(commonFashionDto.getImgContinue());
		commonFashionForUpdate.setImgDisplay(commonFashionDto.getImgDisplay());
		commonFashionForUpdate.setListImg(commonFashionDto.getListImg());
		commonFashionForUpdate.setModel(commonFashionDto.getModel());
		commonFashionForUpdate.setType(commonFashionDto.getType());
		commonFashionForUpdate.setName(commonFashionDto.getName());
		commonFashionForUpdate.setPrice(commonFashionDto.getPrice());
		
		List<TypeColor> typeColors = new ArrayList<TypeColor>();
		for(Map<String,String> item : commonFashionDto.getTypeColor()) {
			TypeColor typeColor = new TypeColor();
			typeColor.setColor(item.getOrDefault("color", null));
			typeColor.setImgColor(item.getOrDefault("imgColor", null));
			typeColors.add(typeColor);
		}		
		commonFashionForUpdate.setTypeColor(typeColors);
		

		return commonFashionForUpdate;
	
	}
}
