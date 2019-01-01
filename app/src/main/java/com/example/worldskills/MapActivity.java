package com.example.worldskills;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends AppCompatActivity {

    private RecyclerView mapRecyclerView;
    private MapAdapter mapAdapter;
    private Set<ATM> atmSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initATMSet();

        initRecyclerView();

        mapAdapter.setItems(atmSet);
    }

    private void initATMSet() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.areas.su/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MapApi mapApi = retrofit.create(MapApi.class);

        final Call<MessageMap> messageMap = mapApi.map();

        messageMap.enqueue(new Callback<MessageMap>() {
            @Override
            public void onResponse(Call<MessageMap> call, Response<MessageMap> response) {
                Log.println(1,"response","response " + response.body());
                atmSet = new HashSet<>();
                atmSet.addAll(Arrays.asList(response.body().getATMs()));
            }

            @Override
            public void onFailure(Call<MessageMap> call, Throwable t) {
                Log.println(1,"failure","failure " + t);
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
