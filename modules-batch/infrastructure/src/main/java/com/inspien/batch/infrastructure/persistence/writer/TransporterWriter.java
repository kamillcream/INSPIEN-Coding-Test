package com.inspien.batch.infrastructure.persistence.writer;

import com.inspien.batch.domain.Transporter;
import com.inspien.batch.infrastructure.persistence.entity.TransporterEntity;
import com.inspien.batch.infrastructure.persistence.mapper.TransportMapper;
import com.inspien.batch.infrastructure.persistence.repository.TransporterJpaRepository;
import com.inspien.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TransporterWriter implements ItemWriter<Transporter> {

    private final TransporterJpaRepository repo;
    private final TransportMapper mapper;
    private final OrderJpaRepository orderRepo;

    @Override
    @Transactional
    public void write(Chunk<? extends Transporter> chunk) throws Exception {
        List<TransporterEntity> entities =
                chunk.getItems().stream()
                        .map(mapper::toEntity)
                        .toList();

        repo.saveAll(entities);

        entities.forEach(
                entity -> orderRepo.markAsSent(entity.getOrderId())
        );
    }
}
