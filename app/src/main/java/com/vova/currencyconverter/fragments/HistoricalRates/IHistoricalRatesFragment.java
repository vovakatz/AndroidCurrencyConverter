package com.vova.currencyconverter.fragments.HistoricalRates;

public interface IHistoricalRatesFragment
{
    void displayError(String error);
    void drawGraph(String baseCurrency, String targetCurrency);
}
