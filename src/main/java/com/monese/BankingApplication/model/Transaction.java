package com.monese.BankingApplication.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PUBLIC)
@Getter
@IdClass(TransactionId.class)
public class Transaction {

    @Id
    private UUID transactionId;

    @Id
    @Enumerated(EnumType.STRING)
    private TransactionId.TransactionType transactionType = TransactionId.TransactionType.DEBIT;

    @NotNull
    private Long targetAccountId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private Long sourceId;

    private String comment;
    private BigDecimal resultingBalance;

    @CreationTimestamp
    private Date createdDate;

    public Transaction invertTransaction() {
        return Transaction.builder()
                .transactionId(transactionId)
                .sourceId(sourceId)
                .targetAccountId(targetAccountId)
                .transactionType(transactionType.toggle())
                .amount(amount)
                .comment(comment).build();
    }

}
