package com.order.service.order_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.service.order_service.Entity.Orders;
import com.order.service.order_service.Repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    public Orders createOrder(Orders order){
        return orderRepository.save(order);
    }

    public Orders findByItem(String item){
        return orderRepository.findByItem(item);
    }

    public void listAll()
    {
        System.out.println(orderRepository.findAll());
    }
}
