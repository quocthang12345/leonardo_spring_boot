package com.leonardo.document;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.leonardo.document.resource.AuthProvider;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Document(collection = "user")
public class UserDocs extends Common{
	@NotNull
	@Field(value = "username")
	private String username;
	
	@Field(value = "fullname")
	private String fullname;
	
	@NotNull
	@Field("email")
    private String email;
	
	@NotNull
	@Field(value = "password")
	private String password;
	
	@Field(value = "phoneNumber")
	private String phoneNumber;
	
	private String imageUrl;
	
	@Field(value = "address")
	private String address;
	
	@Field(value = "city")
	private String city;
	
	@Field(value = "status")
	private int status;
	
	@NotNull
    @DBRef(lazy=true)
    @Field("role")
    private Set<RoleDocs> roles = new HashSet<>();
    
    @Field("emailVerified")
    private Boolean emailVerified = false;
    
    @Field("verifyCode")
    private String verifyCode;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;
	
}
