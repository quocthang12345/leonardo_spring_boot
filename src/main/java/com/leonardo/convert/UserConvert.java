package com.leonardo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leonardo.document.UserDocs;
import com.leonardo.document.dto.UserDTO;

@Component
public class UserConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserDTO toDTO(UserDocs userDocs) {
		UserDTO user = modelMapper.map(userDocs,UserDTO.class);
		return user;
	}
	
	public UserDocs toDocs(UserDTO userDto) {
		UserDocs user = modelMapper.map(userDto, UserDocs.class);
	    return user;
	}
}
