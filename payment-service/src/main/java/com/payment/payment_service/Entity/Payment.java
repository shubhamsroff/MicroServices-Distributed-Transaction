package com.payment.payment_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseAudity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    private String status;
    private String userId;
    private String orderNumber;

    private String item;
    private String paymentMode;
    private String price; // Changed from String to BigDecimal
}
