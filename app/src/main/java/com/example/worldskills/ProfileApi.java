package com.example.worldskills;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfileApi {
    @POST("login")
    Call<Token> login(@Body LoginData loginData);

    @DELETE("logout")
    Call<ResponseBody> logout(@Query("token") String token);

    @GET("user")
    Call<User> getUserInfo(@Query("token") String token);

    @PATCH("user")
    Call<User> changePassword(@Query("token") String token, @Body NewLoginData newLoginData);

    @PATCH("user")
    Call<User> changeLogin(@Query("token") String token, @Body NewLoginData newLoginData);
}
