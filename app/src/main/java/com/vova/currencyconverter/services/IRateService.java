package com.vova.currencyconverter.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public interface IRateService
{
    void populateRates(Boolean triggerEvent);
    void populateHistoricRates(Date date, String baseCurrency, String targetCurrency);
    BigDecimal convert (int amount, String fromCurrency, String toCurrency);
}
