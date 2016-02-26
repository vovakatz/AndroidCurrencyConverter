package com.vova.currencyconverter.activities.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.vova.currencyconverter.R;
import com.vova.currencyconverter.activities.setting.SettingsActivity;
import com.vova.currencyconverter.services.IRateService;
import com.vova.currencyconverter.services.RateService;

public class BaseActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh_rates:
                IRateService rateService = new RateService();
                rateService.populateRates(true);
                return true;
            case R.id.action_quit:
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                System.exit(0);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
