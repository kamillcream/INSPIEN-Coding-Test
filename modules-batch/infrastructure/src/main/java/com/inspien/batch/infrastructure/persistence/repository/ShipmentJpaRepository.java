package com.inspien.batch.infrastructure.persistence.repository;

import com.inspien.batch.infrastructure.persistence.entity.ShipmentEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentJpaRepository extends JpaRepository<ShipmentEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT s FROM ShipmentEntity s
            ORDER BY s.shipmentId DESC
            """)
    List<ShipmentEntity> findLatestShipment(Pageable pageable);
}
