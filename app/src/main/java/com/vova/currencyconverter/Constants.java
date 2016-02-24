package com.vova.currencyconverter;

public class Constants
{
    public static final String CURRENCY_URL = "http://api.fixer.io";
    public static final String DEFAULT_BASE_CURRENCY = "USD";
    public static final String DEFAULT_TARGET_CURRENCY = "EUR";
    public static final String RATES_PREFERENCES = "RATES_PREFERENCES";
    public static final String EXCHANGE_RATES = "EXCHANGE_RATES";
    public static final String HISTORICAL_EXCHANGE_RATES = "HISTORICAL_EXCHANGE_RATES";

    public static final String[] CURRENCY_CODES = {
            "AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HRK",
            "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN",
            "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR"
    };

    public static final String[] CURRENCY_NAMES = {
            "Australian Dollar", "Bulgarian Lev", "Brazilian Real", "Canadian Dollar", "Swiss Franc",
            "Yuan Renminbi", "Czech Koruna", "Danish Krone", "Euro", "Pound Sterling",
            "Hong Kong Dollar", "Croatian Kuna", "Hungarian Forint", "Indonesian Rupiah",
            "Israeli New Shekel", "Indian Rupee", "Japanese Yen", "Korean Won", "Mexican Nuevo Peso",
            "Malaysian Ringgit", "Norwegian Krone", "New Zealand Dollar", "Philippine Peso",
            "Polish Zloty", "Romanian New Leu", "Belarussian Ruble", "Swedish Krona",
            "Singapore Dollar", "Thai Baht", "Turkish Lira", "US Dollar", "South African Rand"
    };
}
