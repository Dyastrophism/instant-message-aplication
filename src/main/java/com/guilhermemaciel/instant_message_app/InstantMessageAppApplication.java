package com.guilhermemaciel.instant_message_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InstantMessageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstantMessageAppApplication.class, args);
	}

}
