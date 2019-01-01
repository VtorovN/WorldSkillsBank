package com.example.worldskills;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApi {

    @GET("latest?access_key=f6898a7448c8a3e1e3113c532aea39f5&symbols=RUB,USD") //Add other currencies if necessary
    Call<MessageCurrency> latest();

    @GET("{date}?access_key=f6898a7448c8a3e1e3113c532aea39f5&symbols=RUB,USD") //Here too
    Call<MessageCurrency> historical(@Path("date") String date); //format YYYY-MM-DD
}
