package com.vova.currencyconverter.activities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.vova.currencyconverter.R;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener
{
    private EditText txtError;
    private EditText txtAmount;
    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);
        txtError = (EditText) findViewById(R.id.txtError);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        findViewById(R.id.btnConvert).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.validateInput(txtAmount.getText().toString());
    }

    @Override
    public void displayError(String error)
    {
        txtError.setText(error);
    }

    @Override
    public void displayRates()
    {

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
