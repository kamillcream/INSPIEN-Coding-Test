package com.inspien.batch.infrastructure.persistence.repository;

import com.inspien.batch.infrastructure.persistence.entity.TransporterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransporterJpaRepository extends JpaRepository<TransporterEntity, String> {
}
