package com.order.service.order_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.service.order_service.Entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    Orders findByItem(String item);
}
