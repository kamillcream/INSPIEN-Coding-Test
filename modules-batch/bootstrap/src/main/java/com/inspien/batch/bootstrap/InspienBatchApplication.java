package com.inspien.batch.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "com.inspien.batch", "com.inspien.infrastructure.mapper"})
@EntityScan(basePackages = {
        "com.inspien.batch.infrastructure.persistence.entity",
        "com.inspien.infrastructure.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.inspien.batch.infrastructure.persistence.repository",
        "com.inspien.infrastructure.persistence.repository"
})
@EnableScheduling
public class InspienBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(InspienBatchApplication.class, args);
    }
}