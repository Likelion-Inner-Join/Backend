package com.likelion.innerjoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InnerjoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnerjoinApplication.class, args);
	}

}
