package com.payment.payment_service.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment_service.Entity.Payment;
import com.payment.payment_service.Entity.PaymentStatus;
import com.payment.payment_service.Entity.TransactionData;
import com.payment.payment_service.Service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/prepare-payment")
    public ResponseEntity<String> prepareOrder(@RequestBody TransactionData transactionData){
        
        try{
           
            Payment payment=new Payment();
            modelMapper.map(transactionData,payment);
            payment.setStatus(PaymentStatus.PENDING.name());
            paymentService.savePayment(payment);
            if(shouldFailedDuringPrepare()){
                throw new RuntimeException("Prepare Phase failed for order"+transactionData.getOrderNumber());
            }
            return ResponseEntity.ok("Ordered Prepared Successfully!");
        } 
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during order preparation");
        }
    }
    public boolean shouldFailedDuringPrepare(){
        return false;
    }

    @PostMapping("/commit-payment")
    public ResponseEntity<String> commitOrder(@RequestBody TransactionData data){
        Payment payment=paymentService.paymentByItem(data.getItem());

        if(payment!=null && payment.getStatus().equalsIgnoreCase(PaymentStatus.PENDING.name())){
            payment.setStatus(PaymentStatus.APPROVED.name());
            paymentService.savePayment(payment);
            return ResponseEntity.ok("Payment Approved Successfully!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment could not approved");
    }

    @PostMapping("/rollback-payment")
    public ResponseEntity<String> rollbackOrder(@RequestBody TransactionData transactionData){
        Payment payment=paymentService.paymentByItem(transactionData.getItem());

        if(payment!=null && payment.getStatus().equalsIgnoreCase(PaymentStatus.PENDING.name())){
            payment.setStatus(PaymentStatus.ROLLBACK.name());
            paymentService.savePayment(payment);
            return ResponseEntity.ok("Payment Rollback Successfully!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment Can't rollback");
   
    }

}
