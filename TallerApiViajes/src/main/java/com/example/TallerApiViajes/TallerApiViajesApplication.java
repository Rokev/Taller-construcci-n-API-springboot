package com.example.TallerApiViajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Las capas del proyecto viven fuera del paquete com.example.TallerApiViajes,
 * por eso se indican explicitamente los paquetes a escanear.
 */
@SpringBootApplication(scanBasePackages = {
		"com.example.TallerApiViajes",
		"BussisnesCatLayer",
		"persistanceLayer",
		"presentationLayer"
})
@EntityScan(basePackages = "persistanceLayer.entity")
@EnableJpaRepositories(basePackages = "persistanceLayer.repository")
public class TallerApiViajesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerApiViajesApplication.class, args);
	}

}
