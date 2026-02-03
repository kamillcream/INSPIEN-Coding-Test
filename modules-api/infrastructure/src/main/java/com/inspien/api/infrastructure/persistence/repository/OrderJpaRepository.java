package com.inspien.api.infrastructure.persistence.repository;

import com.inspien.api.infrastructure.persistence.entity.OrderEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT o FROM OrderEntity o
            ORDER BY o.orderId DESC
            """)
    List<OrderEntity> findLatestOrder(Pageable pageable);


    @Query("""
            SELECT o FROM OrderEntity o
            WHERE o.status = 'N'
            ORDER BY o.orderId ASC
            """)
    List<OrderEntity> findPendingOrders();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
    UPDATE OrderEntity o
    SET o.status = 'Y'
    WHERE o.orderId = :orderId
    """)
    void markAsY(@Param("orderId") String orderId);
}
