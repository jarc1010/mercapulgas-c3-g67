package com.mercapulgas.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbApirestApplication {

	private static final Logger log = LoggerFactory.getLogger(SbApirestApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SbApirestApplication.class, args);
		
		System.out.println("Prueba para ver si imprime de sb. by jarc");
		log.info("esto es info. by jarc");
		System.out.println("-----");		
		log.error("esto es un error. by jarc");
		System.out.println("-----");
		log.warn("esto es un warning. by jarc");
	}

}
