package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackendApiApplication {
	
	

	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(BackendApiApplication.class, args);
		
	}

}
