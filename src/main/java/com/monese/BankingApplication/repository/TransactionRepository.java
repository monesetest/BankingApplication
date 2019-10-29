package com.monese.BankingApplication.repository;

import com.monese.BankingApplication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
