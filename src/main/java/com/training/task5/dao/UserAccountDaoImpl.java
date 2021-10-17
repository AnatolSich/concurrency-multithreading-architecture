package com.training.task5.dao;

import com.training.task5.model.Currency;
import com.training.task5.model.UserAccount;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class UserAccountDaoImpl implements UserAccountDao {

    // Create folder and add full path to it
    private static final String FILE_STORAGE = "/src/main/resources/accountFiles/";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveUserAccount(UserAccount account) {
        String fullFileName = createFileName(account.getName(), account.getAccountCurrency());
        File file = new File(fullFileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(
                             new FileOutputStream(file)
                     )) {
            objectOutputStream.writeObject(account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserAccount getUserAccount(String name, Currency currency) {
        log.info("Getting user {} {} from storage by path: {} | {}", name, currency, FILE_STORAGE, Thread.currentThread());
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream(createFileName(name, currency)))) {
            return (UserAccount) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.warn("User with name = {} {}, not found | {}", name, currency, Thread.currentThread());
        }
        return null;
    }

    private String createFileName(String name, Currency currency) {
        return FILE_STORAGE + name + "_" + currency;
    }
}
