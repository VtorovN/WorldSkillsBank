package com.example.worldskills;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryProfile {

    public static void getUserInfo(final UserDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
    }

    public static void getUserToken(String username, String password, final LoginDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<Token> login = profileApi.login(username, password);
        login.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                listener.onGetToken(Token.getToken());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.onGetToken(null); //try/catch construction when called
            }
        });
    }
}
