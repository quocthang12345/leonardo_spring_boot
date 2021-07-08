package com.leonardo.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.leonardo.document.resource.ERole;

import lombok.Data;

@Data
@Document(collection = "roles")
public class RoleDocs extends Common{
	
	@Field("roleName")
	private String roleName;
}
