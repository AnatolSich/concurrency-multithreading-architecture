package com.training.task5.model;

import lombok.Data;

@Data
public class ExchangeType {

    private final Currency from;
    private final Currency to;

   public ExchangeType(Currency from, Currency to) {
        this.from = from;
        this.to = to;
    }
}
