package com.inspien.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.inspien"})
@EnableJpaRepositories(basePackages = {"com.inspien.**.persistence.repository"})
@EntityScan(basePackages = {"com.inspien.infrastructure.persistence.entity"})
@EnableScheduling
public class InspienApplication {

	public static void main(String[] args) {
		SpringApplication.run(InspienApplication.class, args);
	}

}
