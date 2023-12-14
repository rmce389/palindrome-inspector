package com.rmcevoy.palindrome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PalindromeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalindromeApplication.class, args);
	}

}
