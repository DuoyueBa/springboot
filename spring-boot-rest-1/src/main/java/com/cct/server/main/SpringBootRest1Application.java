package com.cct.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cct"})
public class SpringBootRest1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRest1Application.class, args);
	}

}
