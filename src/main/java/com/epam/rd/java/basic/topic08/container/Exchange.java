package com.epam.rd.java.basic.topic08.container;

import java.util.*;

public class Exchange {
    private final List<Currency> currencies;

    public Exchange(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void sortByName() {
        currencies.sort(Comparator.comparing(Currency::getName));
    }

    public void sortByRate() {
        currencies.sort(Comparator.comparingDouble(Currency::getRate));
    }

    public void sortByAbr() {
        currencies.sort(Comparator.comparing(Currency::getAbr));
    }
}