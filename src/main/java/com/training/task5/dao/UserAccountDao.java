package com.training.task5.dao;


import com.training.task5.model.Currency;
import com.training.task5.model.UserAccount;

public interface UserAccountDao {

    void saveUserAccount(UserAccount account);

    UserAccount getUserAccount(String name, Currency currency);
}
