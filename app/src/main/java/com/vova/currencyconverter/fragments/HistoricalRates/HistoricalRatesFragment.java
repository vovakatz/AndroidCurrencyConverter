package com.vova.currencyconverter.fragments.HistoricalRates;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vova.currencyconverter.R;

public class HistoricalRatesFragment extends Fragment implements IHistoricalRatesFragment
{
    private IHistoricalRatesPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        presenter = new HistoricalRatesPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_historical_rates, container, false);
    }
}
