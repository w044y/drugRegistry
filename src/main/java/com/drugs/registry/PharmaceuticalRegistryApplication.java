package com.drugs.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PharmaceuticalRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.drugs.registry.PharmaceuticalRegistryApplication.class, args);
	}

}
