package com.uniroma3.it.gastroguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.uniroma3.it.gastroguide.models")
@SpringBootApplication()
public class  GastroguideApplication {

	public static void main(String[] args) {
		SpringApplication.run(GastroguideApplication.class, args);
	}

}
