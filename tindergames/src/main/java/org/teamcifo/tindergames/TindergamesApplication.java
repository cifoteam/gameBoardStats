package org.teamcifo.tindergames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TindergamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TindergamesApplication.class, args);
	}
}
