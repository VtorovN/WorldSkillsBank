package com.example.worldskills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private Set<Currency> currencySet;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        progressBar = findViewById(R.id.course_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        final TextView textViewDate = findViewById(R.id.course_text_date);
        textViewDate.setText(dateFormat.format(currentDate));

        initCurrencySet();

        initRecyclerView();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        courseRecyclerView.addItemDecoration(decoration);
    }

    private void initCurrencySet() {
        currencySet = new HashSet<>();
        Currency currency = new Currency("USD", getResources().getString(R.string.usd_decoded), R.drawable.us_flag);
        currency.setListener(new Listener() {
            @Override
            public void onGetData() {
                courseAdapter.setItems(currencySet);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        currencySet.add(currency);
    }

    private void initRecyclerView() {
        courseRecyclerView = findViewById(R.id.course_recyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);
    }
}
