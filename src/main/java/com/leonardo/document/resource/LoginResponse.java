package com.leonardo.document.resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String accessToken;
	private String tokenType;
}
