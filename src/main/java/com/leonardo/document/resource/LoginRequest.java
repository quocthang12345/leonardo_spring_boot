package com.leonardo.document.resource;

import lombok.Data;

@Data
public class LoginRequest {
	private String username;
	private String password;
	private int status;
}
