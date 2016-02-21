package com.vova.currencyconverter.activities.main;

import java.util.ArrayList;

/**
 * Created by vkatz on 2/6/16.
 */
public interface IMainView
{
    void displayError(String error);
    void displayRates(String ratesDisplay);
    void bindSpinners(String [] currencies, String defaultBaseCurrency, String defaultTargetCurrency);
    void hideOverlay();
    void displayNoRatesError(String error);
}
