package com.example.worldskills;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MapApi {

    @GET("bankomats")
    Call<MessageMap> map();
}
