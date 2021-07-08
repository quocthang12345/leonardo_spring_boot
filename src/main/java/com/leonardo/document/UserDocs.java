package com.leonardo.document;

import java.util.HashSet;
import java.util.Set;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document(collection = "user")
public class UserDocs extends Common{
	@Field(value = "username")
	private String username;
	
	@Field(value = "password")
	private String password;
	
	@Field(value = "phoneNumber")
	private String phoneNumber;
	
	@Field(value = "address")
	private String address;
	
	@Field(value = "status")
	private int status;
	
    @DBRef(lazy=true)
    @Field("role")
    private Set<RoleDocs> roles = new HashSet<>();
	
}
