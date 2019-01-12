package com.example.worldskills;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class MapActivity extends AppCompatActivity {

    private RecyclerView mapRecyclerView;
    private MapAdapter mapAdapter;
    private ATM[] atmArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        atmArray = MainActivity.getATMs();

        initRecyclerView();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mapRecyclerView.addItemDecoration(decoration);

        mapAdapter.setItems(Arrays.asList(atmArray));
    }

    private void initRecyclerView() {
        mapRecyclerView = findViewById(R.id.map_recyclerView);
        mapRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mapAdapter = new MapAdapter();
        mapRecyclerView.setAdapter(mapAdapter);
    }
}
