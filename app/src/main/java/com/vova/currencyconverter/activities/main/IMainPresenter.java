package com.vova.currencyconverter.activities.main;

/**
 * Created by vkatz on 2/6/16.
 */
public interface IMainPresenter
{
    boolean validateInput(String amount);
    void convert(String amount);
}
