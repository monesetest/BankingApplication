package com.monese.BankingApplication.api;

import com.monese.BankingApplication.model.Account;
import com.monese.BankingApplication.model.Transaction;
import com.monese.BankingApplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "accounts/{id}/statement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountStatement(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findAccountByAccountId(id));
    }

    @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createTransaction(@Valid @RequestBody Transaction transaction) {
        transaction = transactionService.commitTransaction(transaction);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(transaction.getTransactionId()).toUri();
        return ResponseEntity.created(location).body(transaction.getTransactionId());
    }
}
