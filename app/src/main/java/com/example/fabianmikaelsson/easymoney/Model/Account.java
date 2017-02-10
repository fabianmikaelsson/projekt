package com.example.fabianmikaelsson.easymoney.Model;

/**
 * Public class to store accounts.
 */

public class Account {

    private String name;
    private int number;

    public Account(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "" + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
