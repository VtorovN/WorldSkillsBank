package com.example.worldskills.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.worldskills.Listener.CurrencyValueDataListener;
import com.example.worldskills.Listener.Listener;
import com.example.worldskills.DialogFragment.LoginDialogFragment;
import com.example.worldskills.R;
import com.example.worldskills.Model.Rate;
import com.example.worldskills.Repository.RepositoryCurrency;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private double eur = 0;
    private double usd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RepositoryCurrency.getCurrencyValue(new CurrencyValueDataListener() {

            final TextView textViewUSD = findViewById(R.id.main_text_usd);
            final TextView textViewEUR = findViewById(R.id.main_text_eur);

            @Override
            public void onGetValueData(Rate rate) {
                usd = rate.getCurrency("USD");
                eur = rate.getCurrency("EUR");

                DecimalFormat decimalFormat = new DecimalFormat("##.00");

                if (usd != 0) {
                    String textUSD = "USD " + Double.parseDouble(decimalFormat.format(usd));
                    textViewUSD.setText(textUSD);
                }
                else
                    textViewUSD.setText(R.string.error);

                if (eur != 0) {
                    String textEUR = "EUR " + Double.parseDouble(decimalFormat.format(eur));
                    textViewEUR.setText(textEUR);
                }
                else
                    textViewUSD.setText(R.string.error);
            }
        });

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        final TextView textViewDate = findViewById(R.id.main_text_date);
        textViewDate.setText(dateFormat.format(currentDate));
    }

    public void courseButtonClick(View view) {
        Intent courseIntent = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(courseIntent);
    }

    public void mapButtonClick(View view) {
        Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(mapIntent);
    }

    public void signInButtonClick(View view) {
        LoginDialogFragment dialogFragment = new LoginDialogFragment();
        dialogFragment.setListener(new Listener() {
            @Override
            public void onCompletion() {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "signIn");
    }
}
