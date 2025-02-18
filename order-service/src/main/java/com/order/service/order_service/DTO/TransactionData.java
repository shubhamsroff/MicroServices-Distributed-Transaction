package com.order.service.order_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionData {

    private String orderNumber;
    private String item;
    private String price;
    private String paymentMode;
}
