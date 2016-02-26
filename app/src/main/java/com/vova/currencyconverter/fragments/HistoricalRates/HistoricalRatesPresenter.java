package com.vova.currencyconverter.fragments.HistoricalRates;

import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.models.HistoricalRateEvent;
import com.vova.currencyconverter.utils.SharedPreferencesUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
                List<ExchangeRate> historicalRates = SharedPreferencesUtils.getHistoricalExchangeRates();
                Collections.sort(historicalRates, byDate);
                SharedPreferencesUtils.setHistoricalExchangeRates(historicalRates);
                theView.drawGraph(event.baseCurrency, event.targetCurrency);
            }
        }
        else
        {
            theView.displayError("There was an error getting historical rates.  Please try again.");
        }
    }

    private Comparator<ExchangeRate> byDate = new Comparator<ExchangeRate>()
    {
        public int compare(ExchangeRate left, ExchangeRate right)
        {
            if (left.getDateTime().isBefore(right.getDateTime()))
            {
                return -1;
            } else
            {
                return 1;
            }
        }
    };
}
