package com.stock.stock_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.stock_service.Entity.Stock;
import com.stock.stock_service.Repository.StockRepository;

@Service
public class StockService {
    

    @Autowired
    private StockRepository stockRepository;

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }

    public Stock deduct(String productName,Long demandQuantity){
        Stock st=stockRepository.findByProductName(productName);
        if(st==null){
            return null;
        }
        Long totalQuantity=st.getTotalQuantity()-demandQuantity;
        if(totalQuantity>0){
            st.setTotalQuantity(totalQuantity);
            stockRepository.save(st);
            return st;
        }
        else{
            return null;
        }    
    }

    public Stock add(String productName,Long quantity){
        Stock st=stockRepository.findByProductName(productName);
        if(st==null){
         st=new Stock();
         st.setProductName(productName);
         st.setTotalQuantity(quantity);
         stockRepository.save(st);
         return st;
        }    
        else{
            st.setTotalQuantity(st.getTotalQuantity()+quantity);
            stockRepository.save(st);
            return st;
        }
    }
}
