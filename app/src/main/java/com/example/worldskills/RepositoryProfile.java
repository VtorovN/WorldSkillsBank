package com.example.worldskills;

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
        Call<User> userInfo = profileApi.getUserInfo(Token.getCurrentToken());
        userInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                listener.onGetUserData(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onGetUserData(null);
            }
        });
    }

    public static void getUserToken(String username, String password, final LoginDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<Token> login = profileApi.login(new LoginData(username, password));
        login.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    Token.setCurrentToken(response.body().getToken());
                    listener.onGetToken(true, "OK");
                }
                else if(response.code() == 404) listener.onGetToken(false, "Invalid login or password");
                else listener.onGetToken(false, "Unknown error");
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.onGetToken(false, "Server error");
            }
        });
    }
}
