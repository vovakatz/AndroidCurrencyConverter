package com.vova.currencyconverter.fragments.HistoricalRates;

import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.models.HistoricalRateEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HistoricalRatesPresenter implements IHistoricalRatesPresenter
{
    private IHistoricalRatesFragment theView;

    public HistoricalRatesPresenter(IHistoricalRatesFragment view)
    {
        this.theView = view;
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onHistoricalRatePopulated(HistoricalRateEvent event)
    {
        if (event.success)
        {
            AppContext.numberOfCallsInitiated--;
            if (AppContext.numberOfCallsInitiated == 0)
            {
                //TODO: Draw graph
                String test = "";
            }
        }
        else
        {
            //TODO: display and error in the view
            String test = "";
        }
    }
}
