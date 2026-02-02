package com.inspien.batch.bootstrap.config;

import com.inspien.batch.domain.Transporter;
import com.inspien.infrastructure.persistence.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchStepConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final ItemReader<OrderEntity> reader;
    private final ItemProcessor<OrderEntity, Transporter> processor;
    private final ItemWriter<Transporter> writer;

    @Bean
    public Step transportStep() {
        return new StepBuilder("transportStep", jobRepository)
                .<OrderEntity, Transporter>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
