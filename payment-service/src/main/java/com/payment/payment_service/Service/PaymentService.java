package com.payment.payment_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.payment_service.Entity.Payment;
import com.payment.payment_service.Repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;


    public Payment savePayment(Payment payment){
        return paymentRepository.save(payment);
    }

    public Payment paymentByItem(String item){
        return paymentRepository.findByItem(item);
    }
   
}
