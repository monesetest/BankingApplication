package com.monese.BankingApplication.service;

import com.monese.BankingApplication.error.AccountNotFoundException;
import com.monese.BankingApplication.error.TransactionFailedRollbackException;
import com.monese.BankingApplication.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void commitTransactionThrowsAccountNotFoundException() {
        Transaction transaction = Transaction.builder()
                .targetAccountId(99L)
                .amount(new BigDecimal(10))
                .sourceId(1L)
                .build();


        assertThrows(AccountNotFoundException.class, () -> {
            transactionService.commitTransaction(transaction);
        });
    }

    @Test
    public void sourceAccountAndTargetAccountCannotBeTheSame() {
        Transaction transaction = Transaction.builder()
                .targetAccountId(1L)
                .amount(new BigDecimal(99))
                .sourceId(1L)
                .build();


        assertThrows(TransactionFailedRollbackException.class, () -> {
            transactionService.commitTransaction(transaction);
        });
    }
}
