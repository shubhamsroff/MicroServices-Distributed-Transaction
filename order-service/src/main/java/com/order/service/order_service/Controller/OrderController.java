package com.order.service.order_service.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.order.service.order_service.DTO.TransactionData;
import com.order.service.order_service.Entity.Orders;
import com.order.service.order_service.Entity.OrderStatus;
import com.order.service.order_service.Service.OrderService;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/prepare-order")
    public ResponseEntity<String> prepareOrder(@RequestBody TransactionData transactionData){
        
        try{
            orderService.listAll();
            Orders order=new Orders();
            System.out.println(transactionData);
            modelMapper.map(transactionData,order);
            order.setItem(transactionData.getItem());
            order.setOrderNumber(transactionData.getOrderNumber());
            order.setPaymentMode(transactionData.getPaymentMode());
            order.setOrderStatus(OrderStatus.PREPARED.name());
            orderService.createOrder(order);
            if(shouldFailedDuringPrepare()){
                throw new RuntimeException("Prepare Phase failed for order"+transactionData.getOrderNumber());
            }
            return ResponseEntity.ok("Order Prepared Successfully");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error During Order Preparation!");
        }
    }
    public boolean shouldFailedDuringPrepare(){
        return false;
    }

    @PostMapping("/commit-order")
    public ResponseEntity<String> commitOrder(@RequestBody TransactionData transactionData){
        Orders order=orderService.findByItem(transactionData.getItem());
        if(order!=null && order.getOrderStatus().equalsIgnoreCase(OrderStatus.PREPARED.name())){
            order.setOrderStatus(OrderStatus.COMMITTED.name());
            orderService.createOrder(order);
            return ResponseEntity.ok("Order Committed Successfully!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Order can't be committed!");
    }

    @PostMapping("/rollback-order")
    public ResponseEntity<String>   rollbackOrder(@RequestBody TransactionData transactionData){
        try{
            Orders order;
            try{
             order=orderService.findByItem(transactionData.getItem());
        }
        catch(Exception e){
            order=new Orders();
            System.out.println(e.getMessage());
        }
            if(order!=null && order.getOrderStatus().equalsIgnoreCase(OrderStatus.PREPARED.name())){
            order.setOrderStatus(OrderStatus.ROLLBACK.name());
            orderService.createOrder(order);
            return ResponseEntity.ok("Order Rolled back Successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't Rollback");
    }
    catch(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Error"+e.getMessage());
    }
    }
}
