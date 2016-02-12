package com.vova.currencyconverter.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vova.currencyconverter.R;
import com.vova.currencyconverter.net.RateService;

public class MainActivity extends AppCompatActivity implements IMainView
{
    private TextView txtError;
    private EditText txtAmount;
    private TextView txtResults;
    private RelativeLayout layoutOverlay;
    private RelativeLayout layoutMain;
    private TextView txtOverlayText;
    private ProgressBar prgLoad;
    private Button btnRefresh;
    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this, new RateService());
        txtError = (TextView) findViewById(R.id.txtError);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        txtResults = (TextView)findViewById(R.id.txtResults);
        layoutOverlay = (RelativeLayout)findViewById(R.id.layoutOverlay);
        layoutMain = (RelativeLayout)findViewById(R.id.layoutMain);
        txtOverlayText = (TextView)findViewById(R.id.txtOverlayText);
        prgLoad = (ProgressBar)findViewById(R.id.prgLoad);
        btnRefresh = (Button)findViewById(R.id.btnRefresh);

        txtResults.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnConvert:
                presenter.convert(txtAmount.getText().toString());
                break;
            case R.id.btnRefresh:
                prgLoad.setVisibility(View.VISIBLE);
                txtOverlayText.setText(R.string.please_wait);
                presenter.refreshRates();
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
