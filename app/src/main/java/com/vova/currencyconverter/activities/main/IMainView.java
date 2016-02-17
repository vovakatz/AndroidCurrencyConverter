package com.vova.currencyconverter.activities.main;

import java.util.ArrayList;

/**
 * Created by vkatz on 2/6/16.
 */
public interface IMainView
{
    void displayError(String error);
    void displayRates(String ratesDisplay);
    void bindSpinners(ArrayList<String> currencies, int defaultFromCurrencyPosition, int defaultToCurrencyPosition);
    void hideOverlay();
    void displayNoRatesError(String error);
}
