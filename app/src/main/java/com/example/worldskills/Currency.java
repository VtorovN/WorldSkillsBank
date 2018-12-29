package com.example.worldskills;

import android.graphics.drawable.Drawable;
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
    private double course;
    private int courseChangeArrowId, flagId;
    private Retrofit retrofit;
    private MessagesApi messagesApi;
    private Call<Message> messageLatest, messageHistorical;
    private Date pastDate;
    private SimpleDateFormat dateFormat;

    public Currency(String code, String decoded, int flagId) {
        this.code = code.toUpperCase();
        this.decoded = decoded;
        this.flagId = flagId;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        messagesApi = retrofit.create(MessagesApi.class);
        dateFormat = new SimpleDateFormat("YYYY-dd-MM");
        updateCourse();
    }

    public String getCode() { return code; }

    public String getDecoded() { return decoded; }

    public int getCourseChangeArrowId() { return courseChangeArrowId; }

    public int getFlagId() { return flagId; }

    public double getCourse() { return course; }

    public void updateCourse() {
        messageLatest = messagesApi.latest();

        messageLatest.enqueue(new Callback<Message>() {

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.println(1,"response","response " + response.body());
                if (response.body().getSuccess()) {
                    course = response.body().getRates().getCurrency(code);
                }
                else {
                    Log.println(1, "response", "response " + response.body());
                    course = -1;
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.println(1,"failure","failure " + t);
                course = -1;
            }
        });

        pastDate = new Date((long)((new Date()).getTime() - 8.64e+7));
        messageHistorical = messagesApi.historical(dateFormat.format(pastDate));

        messageHistorical.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.println(1, "response", "response" + response.body());
                if (response.body().getSuccess()) {
                    courseChangeArrowId = response.body().getRates().getCurrency(code) < course ? R.drawable.arrow_up_green : R.drawable.arrow_down_red;
                }
                else {
                    course = -1;
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.println(1, "failure", "failure " + t);
                course = -1;
            }
        });
    }
}
