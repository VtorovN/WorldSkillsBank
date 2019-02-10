package com.example.worldskills;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProfileApi {
    @FormUrlEncoded
    @POST("login")
    Call<Token> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
