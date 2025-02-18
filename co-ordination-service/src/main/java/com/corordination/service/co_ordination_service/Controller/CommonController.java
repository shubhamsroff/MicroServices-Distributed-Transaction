package com.corordination.service.co_ordination_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.corordination.service.co_ordination_service.DTO.TransactionData;

@RestController
public class CommonController {

    @Autowired
    private CoordinationService coordinationService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/initiate_2pc")
    public String initialTwoPhase(@RequestBody TransactionData transactionData){

        if(coordinationService.callPrepareService(transactionData)){
            if(coordinationService.callCommitPhase(transactionData)){
                return "Transaction Committed Successfully";
            }
            else
            coordinationService.callRollback(transactionData);
        }
        // coordinationService.callRollback(transactionData);
        return "Transaction Rollback ii";
    }
    
}
