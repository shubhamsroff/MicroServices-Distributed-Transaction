package com.corordination.service.co_ordination_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.corordination.service.co_ordination_service.DTO.TransactionData;

@Service
public class CoordinationService {


    @Autowired
    private RestTemplate restTemplate;
    public boolean callPrepareService(TransactionData transactionData){
        try{

            boolean isOrderSuccess=callServices("http://localhost:8081/prepare-order",transactionData);
            boolean isPaymentSuccess=callServices("http://localhost:8080/prepare-payment",transactionData);
            return isOrderSuccess && isPaymentSuccess;
        }

        catch(Exception e){
            return false;
        }
    }
    public boolean callServices(String url,TransactionData transactionData){
        ResponseEntity<String> response=restTemplate.postForEntity(url, transactionData, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean callCommitPhase(TransactionData transactionData){
        boolean isOrderCommitted=callCommitService("http://localhost:8081/commit-order",transactionData);
        boolean isPaymentCommitted=callCommitService("http://localhost:8080/commit-payment",transactionData);
        return isOrderCommitted && isPaymentCommitted;
    }

    public boolean callCommitService(String url,TransactionData transactionData){
        ResponseEntity<String> response=restTemplate.postForEntity(url, transactionData, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public void callRollback(TransactionData transactionData){
        boolean isOrderSuccess = callRollbackService("http://localhost:8081/rollback-order",transactionData);
        boolean isPaymentSuccess=callRollbackService("http://localhost:8080/rollback-payment",transactionData);
    }

    public boolean callRollbackService(String url,TransactionData transactionData)  {
        return restTemplate.postForEntity(url, transactionData, Void.class).getStatusCode().is2xxSuccessful(); 
    }
}
