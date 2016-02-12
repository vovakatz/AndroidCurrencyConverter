package com.vova.currencyconverter.activities.main;


import com.vova.currencyconverter.models.Currency;
import com.vova.currencyconverter.models.MessageEvent;
import com.vova.currencyconverter.net.IRateService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void convert(String amount)
    {
        theView.displayError("");
        if (validateInput(amount))
        {
            StringBuilder sb = new StringBuilder();
            ArrayList<Currency> currencies = service.GetCurrencies("USD", Integer.parseInt(amount));

            Iterator it = currencies.iterator();
            while (it.hasNext())
            {
                sb.append(((Currency)it.next()).toString());
            }
            theView.displayRates(sb.toString());
        }
        else
        {
            theView.displayError("Please enter a valid number.");
        }
    }

    public void refreshRates()
    {
        service.PopulateRates();
    }

    @Subscribe
    public void onRatesPopulated(MessageEvent event)
    {
        if (event.success)
            theView.hideOverlay();
        else
            theView.displayNoRatesError("There was an issue getting the rates.  Please try again.");
    }

    private boolean validateInput(String amount)
    {
        if (amount.length() > 0 && Integer.parseInt(amount) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
