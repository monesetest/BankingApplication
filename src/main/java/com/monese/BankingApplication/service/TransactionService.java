package com.monese.BankingApplication.service;

import com.monese.BankingApplication.error.AccountNotFoundException;
import com.monese.BankingApplication.error.InsufficientBalanceException;
import com.monese.BankingApplication.error.TransactionFailedRollbackException;
import com.monese.BankingApplication.model.Account;
import com.monese.BankingApplication.model.Transaction;
import com.monese.BankingApplication.repository.AccountRepository;
import com.monese.BankingApplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Account findAccountByAccountId(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    private void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction commitTransaction(Transaction transaction) {

        if (transaction.getTransactionId() == null) {
            transaction.setTransactionId(UUID.randomUUID());
        }

        if (transaction.getTargetAccountId().equals(transaction.getSourceId())) {
            throw new TransactionFailedRollbackException("Origin account and destination account cannot be the same");
        }

        Account sourceAccount = accountRepository.findById(transaction.getSourceId()).orElseThrow(() -> new AccountNotFoundException(transaction.getSourceId()));

        if (sourceAccount.getBalance().compareTo(transaction.getAmount()) <0) {
            throw new InsufficientBalanceException(sourceAccount.getId());

        } else {
            Account targetAccount = accountRepository.findById(transaction.getTargetAccountId()).orElseThrow(() ->  new AccountNotFoundException(transaction.getTargetAccountId()));

            transaction.setResultingBalance(sourceAccount.debit(transaction.getAmount()));
            save(transaction);

            Transaction creditTransaction = transaction.invertTransaction();
            creditTransaction.setResultingBalance(targetAccount.credit(transaction.getAmount()));
            save(creditTransaction);
        }
        return transaction;
    }
}
