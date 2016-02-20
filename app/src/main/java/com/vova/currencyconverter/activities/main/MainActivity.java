package com.vova.currencyconverter.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.R;
import com.vova.currencyconverter.net.RateService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView
{
    @Bind(R.id.txtError) TextView txtError;
    @Bind(R.id.txtAmount) EditText txtAmount;
    @Bind(R.id.txtResults) TextView txtResults;
    @Bind(R.id.layoutOverlay) RelativeLayout layoutOverlay;
    @Bind(R.id.layoutMain) RelativeLayout layoutMain;
    @Bind(R.id.txtOverlayText) TextView txtOverlayText;
    @Bind(R.id.prgLoad) ProgressBar prgLoad;
    @Bind(R.id.btnRefresh) Button btnRefresh;
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
        txtResults.setMovementMethod(new ScrollingMovementMethod());
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
    public void displayRates(String ratesResult)
    {
        txtResults.setText(ratesResult);
    }

    @Override
    public void bindSpinners(ArrayList<String> currencies, int defaultFromCurrencyPosition, int defaultToCurrencyPosition)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, currencies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFromCurrency.setAdapter(adapter);
        spnToCurrency.setAdapter(adapter);

        spnFromCurrency.setSelection(defaultFromCurrencyPosition);
        spnToCurrency.setSelection(defaultToCurrencyPosition);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
