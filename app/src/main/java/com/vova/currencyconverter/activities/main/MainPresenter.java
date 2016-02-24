package com.vova.currencyconverter.activities.main;


import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.Constants;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.models.RateEvent;
import com.vova.currencyconverter.services.IRateService;
import com.vova.currencyconverter.utils.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainPresenter implements IMainPresenter
{
    private IMainView theView;
    private IRateService service;

    public MainPresenter(IMainView view, IRateService service)
    {
        this.theView = view;
        this.service = service;
        EventBus.getDefault().register(this);
    }

    public void convert(String amount, String fromCurrency, String toCurrency)
    {
        if (validateInput(amount, fromCurrency, toCurrency))
        {
            BigDecimal convertedAmount = service.convert(Integer.parseInt(amount), fromCurrency, toCurrency);
            DecimalFormat twoDForm = new DecimalFormat("#,###.00");
            theView.displayRates(String.format("%,d", Integer.parseInt(amount)) + fromCurrency + " = " + twoDForm.format(convertedAmount) + toCurrency);
            getHistoricalRates(fromCurrency, toCurrency);
        }
    }

    public void refreshRates()
    {
        service.populateRates(true);
    }

    public void getHistoricalRates(String fromCurrency, String toCurrency)
    {
        DateTime date = DateTime.now();
        AppContext.numberOfCallsInitiated = 0;
        SharedPreferencesUtils.setHistoricalExchangeRates(new ArrayList<ExchangeRate>());
        for (int i = 0; i < 12; i++)
        {
            AppContext.numberOfCallsInitiated++;
            service.populateHistoricalRates(date.toDate(), fromCurrency, toCurrency);
            date = date.minusMonths(1);
        }
    }

    @Subscribe
    public void onRatesPopulated(RateEvent event)
    {
        if (event.success)
        {
            theView.bindSpinners(Constants.CURRENCY_CODES, Constants.DEFAULT_BASE_CURRENCY, Constants.DEFAULT_TARGET_CURRENCY);
            theView.hideOverlay();
        }
        else
            theView.displayNoRatesError("There was an issue getting the rates.  Please try again.");
    }

    private boolean validateInput(String amount, String fromCurrency, String toCurrency)
    {
        theView.displayError("");
        if (amount.length() == 0 || Integer.parseInt(amount) == 0)
        {
            theView.displayError("Please enter a valid number.");
            return false;
        }
        if (fromCurrency == toCurrency)
        {
            theView.displayError("Base and target currencies can't be the same.");
            return false;
        }

        return true;
    }
}
