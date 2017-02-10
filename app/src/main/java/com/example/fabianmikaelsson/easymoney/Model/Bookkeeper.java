package com.example.fabianmikaelsson.easymoney.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Public singleton class to handle info from accounts.
 */

public class Bookkeeper {

    List<Account> accounts = new ArrayList<Account>();

    private static Bookkeeper instance = null;

    private Bookkeeper() {
        addAccounts();
    }

    // Singleton constructor.
    public static Bookkeeper getInstance() {
        if(instance==null) {
            instance = new Bookkeeper();

        }
        return instance;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    private void addAccounts() {
        accounts.add(new Account("Allkonto", 22));
        accounts.add(new Account("Pension", 23));
        accounts.add(new Account("Fondkonto", 24));
    }

    // Add account to accounts.
    public void addAccount(String name, int number) {
        accounts.add(new Account(name, number));
    }

    // Remove account depending on parameter index.
    public void removeAccount(int index) {
        accounts.remove(index);
    }

}
