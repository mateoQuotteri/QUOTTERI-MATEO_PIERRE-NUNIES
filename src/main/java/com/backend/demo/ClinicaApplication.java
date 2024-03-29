package com.backend.demo;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClinicaApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(ClinicaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClinicaApplication.class, args);
		LOGGER.info("3,2,1; ClinicaOdontologica is on...");
	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
