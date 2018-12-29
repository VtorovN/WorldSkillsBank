package com.example.worldskills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private double eur = 0;
    private double usd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        final TextView textViewDate = findViewById(R.id.main_text_date);
        textViewDate.setText(dateFormat.format(currentDate));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessagesApi messagesApi = retrofit.create(MessagesApi.class);

        Call<Message> messageLatest = messagesApi.latest();

        messageLatest.enqueue(new Callback<Message>() {
            final TextView textViewUSD = findViewById(R.id.main_text_usd);
            final TextView textViewEUR = findViewById(R.id.main_text_eur);

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.println(1,"response","response " + response.body());
                if (response.body().getSuccess()) {
                    eur = response.body().getRates().getCurrency("EUR");
                    usd = response.body().getRates().getCurrency("USD");

                    DecimalFormat decimalFormat = new DecimalFormat("##.00");

                    String textUSD = "USD " + Double.parseDouble(decimalFormat.format(usd));
                    textViewUSD.setText(textUSD);

                    String textEUR = "EUR " + Double.parseDouble(decimalFormat.format(eur));
                    textViewEUR.setText(textEUR);
                }
                else {
                    eur = -1;
                    usd = -1;
                    textViewUSD.setText(R.string.error);
                    textViewEUR.setText(R.string.error);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.println(1,"failure","failure " + t);
                eur = -1;
                usd = -1;
                textViewUSD.setText(R.string.error);
                textViewEUR.setText(R.string.error);
            }
        });
    }

    public void courseButtonClick(View view) {
        Intent courseIntent = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(courseIntent);
    }
}
