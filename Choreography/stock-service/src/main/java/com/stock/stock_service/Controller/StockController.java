package com.stock.stock_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stock.stock_service.Entity.Stock;
import com.stock.stock_service.Service.StockService;
import com.stock.stock_service.StockDto.StockDto;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/create-stock")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock){
        Stock st=stockService.createStock(stock);
        return ResponseEntity.status(HttpStatus.OK).body(st);
    }

    @PostMapping("/deduct-stock")
    public ResponseEntity<Stock> deductStock(@RequestBody StockDto stockDto){
        Stock st=stockService.deduct(stockDto.getProductName(), stockDto.getDemandOrAddQuantity());
        if(st==null){
            Stock stock=new Stock();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stock);
        }
        else{
        return ResponseEntity.ok(st);
        }
    }

    @PostMapping("/add-stock")
    public ResponseEntity<Stock> addStock(@RequestBody StockDto stockDto){
        Stock st=stockService.add(stockDto.getProductName(), stockDto.getDemandOrAddQuantity());
        return ResponseEntity.ok(st);
    }
}
