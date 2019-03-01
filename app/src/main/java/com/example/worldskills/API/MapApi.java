package com.example.worldskills.API;

import com.example.worldskills.Model.ATM;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MapApi {

    @GET("bankomats")
    Call<ATM[]> map();
}
