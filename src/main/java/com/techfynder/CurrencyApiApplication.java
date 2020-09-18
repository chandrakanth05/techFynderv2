package com.techfynder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("com.techfynder")
@EnableMongoRepositories("com.techfynder")
public class CurrencyApiApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(CurrencyApiApplication.class, args);
	}

}
