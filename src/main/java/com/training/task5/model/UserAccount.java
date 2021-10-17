package com.training.task5.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserAccount implements Serializable {

    private String name;
    private Currency accountCurrency;
    private BigDecimal accountAmount;

    public UserAccount(String name) {
        this.name = name;
    }

    public UserAccount(String name, Currency accountCurrency, BigDecimal accountAmount) {
        this.name = name;
        this.accountCurrency = accountCurrency;
        this.accountAmount = accountAmount;
    }
}
