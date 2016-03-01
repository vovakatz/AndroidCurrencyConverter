package com.vova.currencyconverter.activities.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vova.currencyconverter.Constants;
import com.vova.currencyconverter.R;
import com.vova.currencyconverter.activities.base.BaseActivity;
import com.vova.currencyconverter.services.RateService;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainView
{
    @Bind(R.id.txtError) TextView txtError;
    @Bind(R.id.txtLastUpdated) TextView txtLastUpdated;
    @Bind(R.id.txtAmount) EditText txtAmount;
    @Bind(R.id.txtResults) TextView txtResults;
    @Bind(R.id.layoutOverlay) RelativeLayout layoutOverlay;
    @Bind(R.id.layoutMain) RelativeLayout layoutMain;
    @Bind(R.id.txtOverlayText) TextView txtOverlayText;
    @Bind(R.id.prgLoad) ProgressBar prgLoad;
    @Bind(R.id.btnRefresh) Button btnRefresh;
    @Bind(R.id.btnConvert) Button btnConvert;
    @Bind(R.id.spnFromCurrency) Spinner spnFromCurrency;
    @Bind(R.id.spnToCurrency) Spinner spnToCurrency;
    @Bind(R.id.toolbar) Toolbar toolbar;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this, new RateService());
        presenter.init();
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnConvert:
                presenter.convert(txtAmount.getText().toString(), spnFromCurrency.getSelectedItem().toString(), spnToCurrency.getSelectedItem().toString());
                break;
            case R.id.btnRefresh:
                prgLoad.setVisibility(View.VISIBLE);
                txtOverlayText.setText(R.string.please_wait);
                presenter.refreshRates();
                break;
            case R.id.btnSwap:
                int selectedFromPosition = spnFromCurrency.getSelectedItemPosition();
                spnFromCurrency.setSelection(spnToCurrency.getSelectedItemPosition());
                spnToCurrency.setSelection(selectedFromPosition);
                break;
        }
    }

    @Override
    public void displayError(String error)
    {
        txtError.setText(error);
    }

    @Override
    public void displayWarning(String warning)
    {
        Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRates(String ratesResult)
    {
        txtResults.setText(ratesResult);
    }

    @Override
    public void bindSpinners(String [] currencies, String defaultBaseCurrency, String defaultTargetCurrency)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, currencies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFromCurrency.setAdapter(adapter);
        spnToCurrency.setAdapter(adapter);

        List<String> currencyList = Arrays.asList(currencies);

        spnFromCurrency.setSelection(currencyList.indexOf(defaultBaseCurrency));
        spnToCurrency.setSelection(currencyList.indexOf(defaultTargetCurrency));
    }

    @Override
    public void hideOverlay()
    {
        layoutOverlay.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayNoRatesError(String error)
    {
        txtOverlayText.setText(error);
        layoutOverlay.setClickable(false);
        prgLoad.setVisibility(View.INVISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLastUpdatedTime(String message)
    {
        txtLastUpdated.setText(message);
    }

    @Override
    public void enableConvertButton()
    {
        btnConvert.setEnabled(true);
    }

    @Override
    public void disableConvertButton()
    {
        btnConvert.setEnabled(false);
    }
}
