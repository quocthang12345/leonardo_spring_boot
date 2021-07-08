package com.leonardo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "leonardo")
public class MongoProperties {
	private String database;
	private String url;
	private String username;
	private String password;
}
