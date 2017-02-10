package com.example.fabianmikaelsson.easymoney.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Public class for categories.
 */
public class EntryCategory {
    private boolean isIncome;

    public EntryCategory(boolean isIncome) {
        this.isIncome = isIncome;
    }

    public List<String> categoriesForIncomeOrExpense() {
        List<String> categories = new ArrayList<String>();

        if(isIncome()) {
            categories.add("Försäljning");
            categories.add("Aktier");
            categories.add("Fonder");
            categories.add("Jobb");
            categories.add("Ränta");
            categories.add("Övrigt");
        }
        else {
            categories.add("Mat");
            categories.add("Fritid");
            categories.add("Boende");
            categories.add("Lån");
            categories.add("Kläder och skor");
            categories.add("Nöje");
            categories.add("Resa");
            categories.add("Skönhet");
            categories.add("Transport");
            categories.add("Träning");
            categories.add("Spel");
            categories.add("Utbildning");
            categories.add("Övrigt");
        }
        return categories;
    }

    public boolean isIncome() {
        return isIncome;
    }
}
