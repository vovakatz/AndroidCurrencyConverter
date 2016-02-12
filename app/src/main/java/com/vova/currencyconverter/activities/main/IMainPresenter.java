package com.vova.currencyconverter.activities.main;

public interface IMainPresenter
{
    void convert(String amount);
    void refreshRates();
}
