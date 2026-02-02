package com.inspien.batch.infrastructure.persistence.reader;

import com.inspien.infrastructure.persistence.entity.OrderEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OrderItemReader {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<OrderEntity> reader() {
        JpaPagingItemReader<OrderEntity> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("""
            SELECT o FROM OrderEntity o
            WHERE o.status = 'N'
        """);
        reader.setPageSize(100);
        return reader;
    }
}