package com.training.task5;

import org.junit.jupiter.api.Test;

public class CurrencyExchangeTest {

    private final CurrencyExchange currencyExchange = new CurrencyExchange();

    @Test
    public void executeTest() {
        currencyExchange.go();
    }
}
