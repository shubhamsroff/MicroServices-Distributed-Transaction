package com.payment.payment_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.payment_service.Entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>{
    
    Payment findByItem(String item);
}
