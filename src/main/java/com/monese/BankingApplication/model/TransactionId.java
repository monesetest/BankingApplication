package com.monese.BankingApplication.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public
class TransactionId implements Serializable {

    private UUID transactionId;

    private TransactionType transactionType;

    public enum TransactionType {
        CREDIT,
        DEBIT;

        TransactionType toggle() {
            if (this.equals(CREDIT))
                return DEBIT;
            else
                return CREDIT;
        }
    }
}
