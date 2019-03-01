package com.example.worldskills.Listener;

import com.example.worldskills.Model.Currency;

public interface CurrencySeparatedDataListener {
    void onGetRealTimeData(Currency currency, CurrencyDataListener listener);
    void onGetHistoricalData(Currency currency, CurrencyDataListener listener);
}