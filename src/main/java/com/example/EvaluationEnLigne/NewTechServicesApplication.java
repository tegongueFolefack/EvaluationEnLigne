package com.example.EvaluationEnLigne;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class NewTechServicesApplication implements CommandLineRunner {
	
	
	@Bean
	public ModelMapper modelMapper () {
		return new ModelMapper();
	} 

	
	
	public static void main(String[] args) {
		SpringApplication.run(NewTechServicesApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
	}

}

