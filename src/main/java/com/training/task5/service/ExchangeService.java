package com.training.task5.service;


import com.training.task5.model.ExchangeType;

import java.math.BigDecimal;

public interface ExchangeService {

    void exchangeCurrency(String userName, ExchangeType exchangeType, BigDecimal value);
}
