package com.kpsec.test.contoller;

import com.kpsec.test.service.transaction.TransactionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Transaction")
@RestController
@RequestMapping("/test/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
}
