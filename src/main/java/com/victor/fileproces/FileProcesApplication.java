package com.victor.fileproces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FileProcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileProcesApplication.class, args);
	}

}
