package com.example.worldskills;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MessagesApi {

    @GET("latest?access_key=f6898a7448c8a3e1e3113c532aea39f5&symbols=USD,RUB")
    Call<Message> messages();
}
