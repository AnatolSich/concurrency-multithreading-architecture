package com.training.task5.service;

import com.training.task5.dao.UserAccountDao;
import com.training.task5.dao.UserAccountDaoImpl;
import com.training.task5.exception.ExchangeException;
import com.training.task5.model.UserAccount;
import com.training.task5.model.Currency;
import com.training.task5.model.ExchangeType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Data
@Slf4j
public class ExchangeServiceImpl implements ExchangeService {

    private final UserAccountDao userAccountDao;

    public ExchangeServiceImpl() {
        this.userAccountDao = new UserAccountDaoImpl();
    }

    @Override
    public synchronized void exchangeCurrency(String userName, ExchangeType exchangeType, BigDecimal amount) {
        log.info("Start exchange currency for user: {}, with params: {}-{}, {} | {}",
                userName, exchangeType.getFrom(), exchangeType.getTo(), amount, Thread.currentThread().getName());

        Currency fromCurrency = exchangeType.getFrom();
        Currency toCurrency = exchangeType.getTo();
        UserAccount fromAccount = userAccountDao.getUserAccount(userName, fromCurrency);
        UserAccount toAccount = userAccountDao.getUserAccount(userName, toCurrency);
        if (Objects.isNull(fromAccount) || Objects.isNull(toAccount)) {
            log.error("User account not found | {}", Thread.currentThread());
            throw new ExchangeException("User account not found");
        }

        BigDecimal fromAmount = fromAccount.getAccountAmount();
        BigDecimal toAmount = toAccount.getAccountAmount();
        if (Objects.isNull(fromAmount) || BigDecimal.valueOf(0).equals(fromAmount) || fromAmount.compareTo(amount) < 0) {
            log.error("Not enough money | {}", Thread.currentThread());
            throw new ExchangeException("Required amount is more than current (Not enough money)");
        }

        fromAccount.setAccountAmount(fromAmount.subtract(amount));
        BigDecimal exchangeResult = getExchangeResult(fromCurrency.getRate(), toCurrency.getRate(), amount);
        toAccount.setAccountAmount(toAmount.add(exchangeResult));

        userAccountDao.saveUserAccount(fromAccount);
        userAccountDao.saveUserAccount(toAccount);
        log.info("End exchange currency for user: {}, with params: {}-{}, {} | {}",
                userName, fromCurrency, toCurrency, amount, Thread.currentThread().getName());
    }

    private BigDecimal getExchangeResult(double fromRate, double toRate, BigDecimal amount){
        BigDecimal rate = BigDecimal.valueOf(fromRate)
                .divide(BigDecimal.valueOf(toRate), RoundingMode.CEILING);
        return amount.multiply(rate);
    }
}
