package com.example.worldskills.Repository;

import com.example.worldskills.API.MapApi;
import com.example.worldskills.Listener.MapDataListener;
import com.example.worldskills.Model.ATM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryMap {
    private static Retrofit retrofit;
    private static MapApi mapApi;

    private static void buildRetrofitAndApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.areas.su/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mapApi = retrofit.create(MapApi.class);
        }
    }

    public static void getMap(final MapDataListener listener) {
        buildRetrofitAndApi();

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
