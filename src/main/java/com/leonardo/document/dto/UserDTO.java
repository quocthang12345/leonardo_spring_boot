package com.leonardo.document.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private String username;
	
	private String password;
	
	private String phoneNumber;
	
	private String address;
	
	private int status;
	
	private List<String> roleId = new ArrayList<String>();
	
	private Date createDate;
	
	private Date modifyDate;
	
	private String createBy;
	
	private String modifyBy;
}
