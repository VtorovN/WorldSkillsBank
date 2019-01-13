package com.example.worldskills;

public interface CurrencySeparatedDataListener {
    void onGetRealTimeData(Currency currency, CurrencyDataListener listener);
    void onGetHistoricalData(Currency currency, CurrencyDataListener listener);
}