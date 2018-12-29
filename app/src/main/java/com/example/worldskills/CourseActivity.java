package com.example.worldskills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private Set<Currency> currencySet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        final TextView textViewDate = findViewById(R.id.course_text_date);
        textViewDate.setText(dateFormat.format(currentDate));

        initCurrencySet();

        initRecyclerView();
        courseAdapter.setItems(currencySet);
    }

    private void initCurrencySet() {
        currencySet = new HashSet<>();
        currencySet.add(new Currency("USD", "@string/usd_decoded", R.drawable.us_flag));
    }

    private void initRecyclerView() {
        courseRecyclerView = findViewById(R.id.course_recyclerView);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);
    }
}
