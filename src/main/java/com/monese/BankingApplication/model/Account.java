package com.monese.BankingApplication.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class Account {

    @Id
    @GeneratedValue
    private Long Id;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private Currency accountCurrency;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "sourceId")
    private Set<Transaction> transactions;

    public BigDecimal debit(BigDecimal amount) {
        return balance = balance.subtract(amount);
    }

    public BigDecimal credit(BigDecimal amount) {
        return balance = balance.add(amount);
    }

}
