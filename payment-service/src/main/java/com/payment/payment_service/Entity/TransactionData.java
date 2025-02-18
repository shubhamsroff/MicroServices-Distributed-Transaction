package com.payment.payment_service.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionData {

        private String orderNumber;
        private String item;
        private String price;
        private String paymentMode;
}
