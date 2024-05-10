package com.JJoINT.CamPuzl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CamPuzlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamPuzlApplication.class, args);
	}

}
