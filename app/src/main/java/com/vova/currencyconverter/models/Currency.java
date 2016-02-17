package com.vova.currencyconverter.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Currency
{
    private String symbol;
    private BigDecimal amount;

    public String getSymbol()
    {
        return symbol;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
}
