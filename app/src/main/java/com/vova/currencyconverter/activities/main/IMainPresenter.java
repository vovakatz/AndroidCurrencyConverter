package com.vova.currencyconverter.activities.main;

import android.os.Bundle;

public interface IMainPresenter
{
    void convert(String amount, String fromCurrency, String toCurrency);
    void refreshRates();
    void init();
}
