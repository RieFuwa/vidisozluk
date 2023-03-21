package com.bkabatas.ssozlukproject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SsozlukApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsozlukApplication.class, args);
	}

}
