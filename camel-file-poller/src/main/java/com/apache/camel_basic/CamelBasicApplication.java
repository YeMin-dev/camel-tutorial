package com.apache.camel_basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.apache.camel_basic.beans")
public class CamelBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelBasicApplication.class, args);
	}

}
