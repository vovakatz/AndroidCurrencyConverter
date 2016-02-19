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

        for(int i = 0; i < AppContext.historicRates.size(); i++)
        {
            Entry entry = new Entry(AppContext.historicRates.get(i).getRates().get(targetCurrency).floatValue(), i);
            vals.add(entry);
            xVals.add(AppContext.historicRates.get(i).getDate());
        }

        ILineDataSet lineDataSet = new LineDataSet(vals, targetCurrency +  " per 1 " + baseCurrency);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(xVals, dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public void displayError(String error)
    {
        Toast.makeText(getContext(), "I will NOT draw the graph.", Toast.LENGTH_SHORT).show();
    }
}
