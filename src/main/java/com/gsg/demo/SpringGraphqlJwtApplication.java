package com.gsg.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringGraphqlJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphqlJwtApplication.class, args);
	}

}
