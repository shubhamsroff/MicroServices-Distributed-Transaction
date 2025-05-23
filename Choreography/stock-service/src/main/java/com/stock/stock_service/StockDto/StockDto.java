package com.stock.stock_service.StockDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {

    private String productName;
    private Long demandOrAddQuantity;
}
