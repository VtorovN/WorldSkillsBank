package com.example.worldskills;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Currency {
    private String  code, decoded;
    private double course, courseChange;
    private int flagId;
    private Retrofit retrofit;
    private CurrencyApi currencyApi;
    private Call<MessageCurrency> messageLatest, messageHistorical;
    private Date pastDate;
    private SimpleDateFormat dateFormat;
    private Listener listener;

    public Currency(String code, String decoded, int flagId) {
        this.code = code.toUpperCase();
        this.decoded = decoded;
        this.flagId = flagId;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        currencyApi = retrofit.create(CurrencyApi.class);
        dateFormat = new SimpleDateFormat("YYYY-dd-MM");
        updateCourse();
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public String getCode() { return code; }

    public String getDecoded() { return decoded; }

    public double getCourseChange() { return courseChange; }

    public int getFlagId() { return flagId; }

    public double getCourse() { return course; }

    public void updateCourse() {
        messageLatest = currencyApi.latest();

        messageLatest.enqueue(new Callback<MessageCurrency>() {

            @Override
            public void onResponse(Call<MessageCurrency> call, Response<MessageCurrency> response) {
                Log.println(1,"response","response " + response.body());
                if (response.body().getSuccess()) {
                    course = response.body().getRates().getCurrency(code);
                    listener.onGetData();
                }
                else {
                    Log.println(1, "response", "response " + response.body());
                    course = -1;
                }
            }

            @Override
            public void onFailure(Call<MessageCurrency> call, Throwable t) {
                Log.println(1,"failure","failure " + t);
                course = -1;
            }
        });

        pastDate = new Date((long)((new Date()).getTime() - 8.64e+7));
        messageHistorical = currencyApi.historical(dateFormat.format(pastDate));

        messageHistorical.enqueue(new Callback<MessageCurrency>() {
            @Override
            public void onResponse(Call<MessageCurrency> call, Response<MessageCurrency> response) {
                Log.println(1, "response", "response" + response.body());
                if (response.body().getSuccess()) {
                    courseChange = course - response.body().getRates().getCurrency(code);
                }
                else {
                    courseChange = 0;
                }
            }

            @Override
            public void onFailure(Call<MessageCurrency> call, Throwable t) {
                Log.println(1, "failure", "failure " + t);
                courseChange = 0;
            }
        });
    }
}
