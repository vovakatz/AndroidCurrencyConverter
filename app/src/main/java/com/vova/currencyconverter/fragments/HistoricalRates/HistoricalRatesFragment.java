package com.vova.currencyconverter.fragments.HistoricalRates;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.R;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.utils.SharedPreferencesUtils;

import java.util.ArrayList;

public class HistoricalRatesFragment extends Fragment implements IHistoricalRatesFragment
{
    private IHistoricalRatesPresenter presenter;
    private View theView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        presenter = new HistoricalRatesPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        theView = inflater.inflate(R.layout.fragment_historical_rates, container, false);
        return theView;
    }

    @Override
    public void drawGraph(String baseCurrency, String targetCurrency)
    {
        LineChart chart = (LineChart) theView.findViewById(R.id.chrRates);

        ArrayList<Entry> vals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<ExchangeRate> historicRates = SharedPreferencesUtils.getHistoricalExchangeRates();

        for(int i = 0; i < historicRates.size(); i++)
        {
            Entry entry = new Entry(historicRates.get(i).getRates().get(targetCurrency).floatValue(), i);
            vals.add(entry);
            xVals.add(historicRates.get(i).getDate());
        }

        ILineDataSet lineDataSet = new LineDataSet(vals, targetCurrency +  " per 1 " + baseCurrency);
        lineDataSet.setDrawFilled(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.setDescription("");
        chart.invalidate();
    }

    @Override
    public void displayError(String error)
    {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }
}
