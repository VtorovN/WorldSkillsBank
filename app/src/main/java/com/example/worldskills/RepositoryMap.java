package com.example.worldskills;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryMap {

    public static void getMap(final MapDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.areas.su/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MapApi mapApi = retrofit.create(MapApi.class);
        Call<ATM[]> messageMap = mapApi.map();

        messageMap.enqueue(new Callback<ATM[]>() {
            @Override
            public void onResponse(Call<ATM[]> call, Response<ATM[]> response) {
                listener.onGetMapData(response.body());
            }

            @Override
            public void onFailure(Call<ATM[]> call, Throwable t) {
                listener.onGetMapData(new ATM[0]);
            }
        });
    }
}
