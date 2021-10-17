package com.leonardo.document;

import java.util.Date;
import java.util.UUID;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class Common {
	@Id
	private String _id;
	
	@Field(value = "createDate")
	@CreatedDate
	private Date createDate;
	
	@Field(value = "updateDate")
	@LastModifiedDate
	private Date updateDate;
	
	@Field(value = "createBy")
	@CreatedBy
	private String createBy;
	
	@Field(value = "updateBy")
	@LastModifiedBy
	private String updateBy;
	
	
	public Common() {
		super();
		this._id = UUID.randomUUID().toString();
	}
	
	
}