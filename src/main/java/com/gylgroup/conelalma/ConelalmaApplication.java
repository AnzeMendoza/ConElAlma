package com.gylgroup.conelalma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ConelalmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConelalmaApplication.class, args);
                
	}

}
