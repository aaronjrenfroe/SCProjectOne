package com.aaronr.googlespringone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HellorestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellorestapiApplication.class, args);
	}
}

