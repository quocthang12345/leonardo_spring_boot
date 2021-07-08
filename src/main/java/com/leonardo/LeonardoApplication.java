package com.leonardo;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.leonardo.service.IFileStorageService;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.leonardo")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableConfigurationProperties
public class LeonardoApplication implements CommandLineRunner {
	 @Resource
	 private IFileStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(LeonardoApplication.class, args);
	}

//	protected void configure(HttpSecurity http) throws Exception {
//	    http.csrf().disable();
//	}
	
	@Override
	public void run(String... arg) throws Exception {
	    storageService.deleteAll();
	    storageService.init();
	 }
}
