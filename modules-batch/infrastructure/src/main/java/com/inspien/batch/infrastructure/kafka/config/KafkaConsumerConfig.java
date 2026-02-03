package com.inspien.batch.infrastructure.kafka.config;

import com.inspien.batch.application.port.in.dto.OrderShipmentCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.inspien.batch.application.port.in.dto");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false); // 헤더에 타입 정보 생략 → DTO가 정확해야 함
        return props;
    }

    @Bean
    public ConsumerFactory<String, OrderShipmentCommand> orderShipmentConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                baseConsumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(OrderShipmentCommand.class, false)); // trusted type
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderShipmentCommand> orderShipmentKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderShipmentCommand>();
        factory.setConsumerFactory(orderShipmentConsumerFactory());
        return factory;
    }
}
