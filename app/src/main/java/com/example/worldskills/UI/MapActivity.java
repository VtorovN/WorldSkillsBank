package com.example.worldskills.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.worldskills.Model.ATM;
import com.example.worldskills.RVAdapter.MapAdapter;
import com.example.worldskills.Listener.MapDataListener;
import com.example.worldskills.R;
import com.example.worldskills.Repository.RepositoryMap;

import java.util.Arrays;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class MapActivity extends AppCompatActivity {

    private RecyclerView mapRecyclerView;
    private MapAdapter mapAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        progressBar = findViewById(R.id.map_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        initRecyclerView();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mapRecyclerView.addItemDecoration(decoration);

        RepositoryMap.getMap(new MapDataListener() {
            @Override
            public void onGetMapData(ATM[] atmArray) {
                mapAdapter.setItems(Arrays.asList(atmArray));
                MapActivity.this.progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initRecyclerView() {
        mapRecyclerView = findViewById(R.id.map_recyclerView);
        mapRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mapAdapter = new MapAdapter();
        mapRecyclerView.setAdapter(mapAdapter);
    }
}
