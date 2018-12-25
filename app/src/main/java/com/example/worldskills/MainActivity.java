package com.example.worldskills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static double eur = 0;
    public static double usd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        String date = dateFormat.format(currentDate);
        final TextView textViewDate = findViewById(R.id.textView_date);
        textViewDate.setText(date);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MessagesApi messagesApi = retrofit.create(MessagesApi.class);

        Call<Message> message = messagesApi.messages();

        message.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.println(1,"response","response " + response.body());
                if (response.body().getSuccess()) {
                    eur = response.body().getRates().getRUB();
                    usd = eur / response.body().getRates().getUSD();

                    DecimalFormat decimalFormat = new DecimalFormat("##.00");
                    eur = Double.parseDouble(decimalFormat.format(eur));
                    usd = Double.parseDouble(decimalFormat.format(usd));

                    final TextView textViewUSD = findViewById(R.id.textView_usd);
                    String textUSD = "USD " + usd;
                    textViewUSD.setText(textUSD);

                    final TextView textViewEUR = findViewById(R.id.textView_eur);
                    String textEUR = "EUR " + eur;
                    textViewEUR.setText(textEUR);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.println(1,"failure","failure " + t);
            }
        });
    }
}
