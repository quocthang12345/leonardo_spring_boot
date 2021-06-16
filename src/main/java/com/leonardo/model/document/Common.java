package com.leonardo.model.document;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Common {
	@JsonIgnore
	private String _id;
	
	@Field(value = "createDate")
	private Date createDate;
	
	@Field(value = "updateDate")
	private Date updateDate;
	
	@Field(value = "createBy")
	private String createBy;
	
	@Field(value = "updateBy")
	private String updateBy;
	
	@Field(value = "isDeleted")
	private Boolean isDeleted;
	
	public Common() {
		super();
		this._id = UUID.randomUUID().toString();
	}
	
}