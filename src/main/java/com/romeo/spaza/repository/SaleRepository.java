package com.romeo.spaza.repository;

import com.romeo.spaza.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findAllByOrderBySaleDateDesc();

    List<Sale> findBySaleDateBetweenOrderBySaleDateDesc(LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(s.totalPrice) FROM Sale s")
    BigDecimal findTotalRevenue();

    @Query("SELECT SUM(s.totalPrice) FROM Sale s WHERE s.saleDate >= :startDate")
    BigDecimal findTotalRevenueSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT s.product.name, SUM(s.quantity), SUM(s.totalPrice) FROM Sale s GROUP BY s.product.name ORDER BY SUM(s.quantity) DESC")
    List<Object[]> findTopSellingProducts();
}
