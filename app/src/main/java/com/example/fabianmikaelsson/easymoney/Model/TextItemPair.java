package com.example.fabianmikaelsson.easymoney.Model;

public class TextItemPair<T> extends Object {

    private String text;
    private T item;

    public String getText() {
        return this.text;
    }

    public T getItem() {
        return this.item;
    }

    public TextItemPair(String text, T item) {
        this.text = text;
        this.item = item;
    }

    @Override
    public String toString() {
        return getText();
    }
}
