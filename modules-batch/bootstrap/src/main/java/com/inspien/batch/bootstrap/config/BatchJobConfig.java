package com.inspien.batch.bootstrap.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    private final JobRepository jobRepository;
    private final Step transportStep;

    @Bean
    public Job transportJob() {
        return new JobBuilder("transportJob", jobRepository)
                .start(transportStep)
                .build();
    }
}
