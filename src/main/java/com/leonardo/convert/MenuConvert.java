package com.leonardo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.model.document.MenuDocs;
import com.leonardo.model.dto.MenuDTO;

@Component
public class MenuConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public MenuDTO toDTO(MenuDocs menuDocs) {
		MenuDTO menu = modelMapper.map(menuDocs,MenuDTO.class);
		return menu;
	}
	
	public MenuDocs toDocs(MenuDTO menuDto) {
		MenuDocs menu = modelMapper.map(menuDto, MenuDocs.class);
	    return menu;
	}
}
