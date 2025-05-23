package com.stock.stock_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.stock_service.Entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    
    Stock findByProductName(String productName);
}
