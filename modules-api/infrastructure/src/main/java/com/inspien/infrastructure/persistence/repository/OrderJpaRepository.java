package com.inspien.infrastructure.persistence.repository;

import com.inspien.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {

    @Query("""
            SELECT o.orderId FROM OrderEntity o
            ORDER BY o.orderId DESC LIMIT 1
            """)
    String findLastId();
}
