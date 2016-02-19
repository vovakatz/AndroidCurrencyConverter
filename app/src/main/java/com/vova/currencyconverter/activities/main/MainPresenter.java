package com.vova.currencyconverter.activities.main;


import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.models.RateEvent;
import com.vova.currencyconverter.net.IRateService;

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
        if (validateInput(amount))
        {
            BigDecimal convertedAmount = service.convert(Integer.parseInt(amount), fromCurrency, toCurrency);
            DecimalFormat twoDForm = new DecimalFormat("#,###.00");
            theView.displayRates(String.format("%,d", Integer.parseInt(amount)) + fromCurrency + " = " + twoDForm.format(convertedAmount) + toCurrency);
            getHistoricRates(fromCurrency, toCurrency);
        }
    }

    public void refreshRates()
    {
        service.populateRates();
    }

    public void getHistoricRates(String fromCurrency, String toCurrency)
    {
        DateTime date = DateTime.now();
        AppContext.numberOfCallsInitiated = 0;
        AppContext.historicRates = new ArrayList<>();
        for (int i = 0; i < 12; i++)
        {
            AppContext.numberOfCallsInitiated++;
            service.populateHistoricRates(date.toDate(), fromCurrency, toCurrency);
            date = date.minusMonths(1);
        }
    }

    @Subscribe
    public void onRatesPopulated(RateEvent event)
    {
        if (event.success)
        {
            ArrayList<String> currencies = service.getCurrenciesList();
            theView.bindSpinners(currencies, currencies.indexOf("USD"), currencies.indexOf("EUR"));
            theView.hideOverlay();
        }
        else
            theView.displayNoRatesError("There was an issue getting the rates.  Please try again.");
    }

    private boolean validateInput(String amount)
    {
        if (amount.length() > 0 && Integer.parseInt(amount) > 0)
        {
            theView.displayError("");
            return true;
        }
        else
        {
            theView.displayError("Please enter a valid number.");
            return false;
        }
    }
}
