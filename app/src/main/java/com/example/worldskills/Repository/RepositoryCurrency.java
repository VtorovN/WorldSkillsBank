package com.example.worldskills.Repository;

import com.example.worldskills.API.CurrencyApi;
import com.example.worldskills.Listener.CurrencyDataListener;
import com.example.worldskills.Listener.CurrencySeparatedDataListener;
import com.example.worldskills.Listener.CurrencyValueDataListener;
import com.example.worldskills.Listener.Listener;
import com.example.worldskills.Model.MessageCurrency;
import com.example.worldskills.Model.Currency;
import com.example.worldskills.Model.Rate;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryCurrency{

    private static boolean realReady, historicalReady;
    private static double historicalCourse;
    private static CurrencySeparatedDataListener sListener;

    public static void getCurrency(final String code, String decoded, int flagId, Listener listener, final CurrencyDataListener cListener) {
        final Currency currency = new Currency(code, decoded, flagId, listener);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyApi currencyApi = retrofit.create(CurrencyApi.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-dd-MM");
        Date pastDate = new Date((long)((new Date()).getTime() - 8.64e+7));

        Call<MessageCurrency> messageLatest = currencyApi.latest();
        Call<MessageCurrency> messageHistorical = currencyApi.historical(dateFormat.format(pastDate));

        historicalCourse = -1;

        realReady = false;
        historicalReady = false;

        sListener = new CurrencySeparatedDataListener() {
            @Override
            public void onGetRealTimeData(Currency currency, CurrencyDataListener listener) {
                realReady = true;
                if (historicalReady) {
                    if (currency.getCourse() == -1 || historicalCourse == -1)
                        currency.setCourseChange(0);
                    else
                        currency.setCourseChange(currency.getCourse() - historicalCourse);
                    listener.onGetCurrencyData(currency);
                }
            }

            @Override
            public void onGetHistoricalData(Currency currency, CurrencyDataListener listener) {
                historicalReady = true;
                if (realReady) {
                    if (currency.getCourse() == -1 || historicalCourse == -1)
                        currency.setCourseChange(0);
                    else
                        currency.setCourseChange(currency.getCourse() - historicalCourse);
                    listener.onGetCurrencyData(currency);
                }
            }
        };

        messageLatest.enqueue(new Callback<MessageCurrency>() {
            @Override
            public void onResponse(Call<MessageCurrency> call, Response<MessageCurrency> response) {
                if (response.body().getSuccess()) {
                    currency.setCourse(response.body().getRates().getCurrency(code));
                    sListener.onGetRealTimeData(currency, cListener);
                }
                else {
                    currency.setCourse(-1);
                    sListener.onGetRealTimeData(currency, cListener);
                }
            }

            @Override
            public void onFailure(Call<MessageCurrency> call, Throwable t) {
                currency.setCourse(-1);
                sListener.onGetRealTimeData(currency, cListener);
            }
        });

        messageHistorical.enqueue(new Callback<MessageCurrency>() {
            @Override
            public void onResponse(Call<MessageCurrency> call, Response<MessageCurrency> response) {
                if (response.body().getSuccess()) {
                    historicalCourse = response.body().getRates().getCurrency(code);
                    sListener.onGetHistoricalData(currency, cListener);
                }
                else {
                    sListener.onGetHistoricalData(currency, cListener);
                }
            }

            @Override
            public void onFailure(Call<MessageCurrency> call, Throwable t) {
                sListener.onGetHistoricalData(currency, cListener);
            }
        });
    }

    public static void getCurrencyValue(final CurrencyValueDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyApi currencyApi = retrofit.create(CurrencyApi.class);

        Call<MessageCurrency> messageLatest = currencyApi.latest();
        messageLatest.enqueue(new Callback<MessageCurrency>() {
            @Override
            public void onResponse(Call<MessageCurrency> call, Response<MessageCurrency> response) {
                if (response.body().getSuccess())
                    listener.onGetValueData(response.body().getRates());
                else
                    listener.onGetValueData(new Rate());
            }

            @Override
            public void onFailure(Call<MessageCurrency> call, Throwable t) {
                listener.onGetValueData(new Rate());
            }
        });
    }
}
