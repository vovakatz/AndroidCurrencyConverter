package com.vova.currencyconverter.models;

public class HistoricalRateEvent
{
    public final Boolean success;
    public final String baseCurrency;
    public final String targetCurrency;

    public HistoricalRateEvent(Boolean success, String baseCurrency, String targetCurrency)
    {
        this.success = success;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }
}
