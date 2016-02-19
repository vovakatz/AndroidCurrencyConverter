package com.vova.currencyconverter.models;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class ExchangeRate
{
    private String base;
    private String date;
    private HashMap<String, BigDecimal> rates;

    public String getBase() {
        return this.base;
    }

    public String getDate() {
        return this.date;
    }

    public DateTime getDateTime()
    {
        return new DateTime(date);
    }

    public HashMap<String, BigDecimal> getRates() {
        return this.rates;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRates(HashMap<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
