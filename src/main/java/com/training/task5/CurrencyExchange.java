package com.training.task5;

import com.training.task5.dao.UserAccountDao;
import com.training.task5.dao.UserAccountDaoImpl;
import com.training.task5.model.UserAccount;
import com.training.task5.service.ExchangeService;
import com.training.task5.service.ExchangeServiceImpl;
import com.training.task5.model.Currency;
import com.training.task5.model.ExchangeType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CurrencyExchange {

    private final UserAccountDao userAccountDao;
    private final ExchangeService exchangeService;

    public CurrencyExchange() {
        this.userAccountDao = new UserAccountDaoImpl();
        this.exchangeService = new ExchangeServiceImpl();
    }

    public void go() {
        userAccountDao.saveUserAccount(new UserAccount("Andrii", Currency.USD, BigDecimal.valueOf(6651.65)));
        userAccountDao.saveUserAccount(new UserAccount("Andrii", Currency.EUR, BigDecimal.valueOf(684.54)));
        userAccountDao.saveUserAccount(new UserAccount("Andrii", Currency.UAH, BigDecimal.valueOf(87694.50)));

        userAccountDao.saveUserAccount(new UserAccount("Pavlo", Currency.USD, BigDecimal.valueOf(984.61)));
        userAccountDao.saveUserAccount(new UserAccount("Pavlo", Currency.EUR, BigDecimal.valueOf(9784.68)));
        userAccountDao.saveUserAccount(new UserAccount("Pavlo", Currency.UAH, BigDecimal.valueOf(321.15)));

        userAccountDao.saveUserAccount(new UserAccount("Tetiana", Currency.USD, BigDecimal.valueOf(987.15)));
        userAccountDao.saveUserAccount(new UserAccount("Tetiana", Currency.EUR, BigDecimal.valueOf(2486.35)));
        userAccountDao.saveUserAccount(new UserAccount("Tetiana", Currency.UAH, BigDecimal.valueOf(5687.63)));
        log.info("***********************************");
        log.info("Accounts before work: {}", userAccountDao.getUserAccount("Andrii", Currency.EUR));
        log.info("Accounts before work: {}", userAccountDao.getUserAccount("Andrii", Currency.UAH));
        log.info("Accounts before work: {}", userAccountDao.getUserAccount("Pavlo", Currency.EUR));
        log.info("Accounts before work: {}", userAccountDao.getUserAccount("Pavlo", Currency.EUR));
        log.info("Accounts before work: {}", userAccountDao.getUserAccount("Tetiana", Currency.UAH));
        log.info("Accounts before work: {}", userAccountDao.getUserAccount("Tetiana", Currency.USD));
        log.info("***********************************");


        Runnable runnable1 = () -> exchangeService.exchangeCurrency("Andrii", new ExchangeType(Currency.EUR, Currency.UAH), BigDecimal.valueOf(1000));
        Runnable runnable2 = () -> exchangeService.exchangeCurrency("Pavlo", new ExchangeType(Currency.EUR, Currency.UAH), BigDecimal.valueOf(1000));
        Runnable runnable3 = () -> exchangeService.exchangeCurrency("Tetiana", new ExchangeType(Currency.UAH, Currency.USD), BigDecimal.valueOf(1000));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(runnable1);
        executorService.submit(runnable2);
        executorService.submit(runnable3);


        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            log.error("InterruptedException:{}", e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("***********************************");
        log.info("Accounts after work: {}", userAccountDao.getUserAccount("Andrii", Currency.EUR));
        log.info("Accounts after work: {}", userAccountDao.getUserAccount("Andrii", Currency.UAH));
        log.info("Accounts after work: {}", userAccountDao.getUserAccount("Pavlo", Currency.EUR));
        log.info("Accounts after work: {}", userAccountDao.getUserAccount("Pavlo", Currency.EUR));
        log.info("Accounts after work: {}", userAccountDao.getUserAccount("Tetiana", Currency.UAH));
        log.info("Accounts after work: {}", userAccountDao.getUserAccount("Tetiana", Currency.USD));
    }
}
