package com.leonardo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.leonardo")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class LeonardoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeonardoApplication.class, args);
	}

	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable();
	}
}
