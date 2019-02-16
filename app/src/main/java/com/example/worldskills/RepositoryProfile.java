package com.example.worldskills;

import android.content.res.Resources;

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

    public static void getUserToken(String username, String password, final DataListener listener) {
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
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                }
                else if(response.code() == 404) listener.onGetData(false,
                        App.getContext().getString(R.string.message_invalid_login_data));
                else listener.onGetData(false,
                            App.getContext().getString(R.string.message_unknown_error));
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }

    public static void changeLogin(String newLogin, final DataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<User> changeLoginData = profileApi.changeLogin(Token.getCurrentToken(), newLogin);
        changeLoginData.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) listener.onGetData(true,
                        App.getContext().getString(R.string.message_success));
                else listener.onGetData(false,
                        App.getContext().getString(R.string.message_unknown_error));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }

    public static void changePassword(String newPassword, final DataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<User> changePasswordData = profileApi.changePassword(Token.getCurrentToken(), newPassword);
        changePasswordData.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) listener.onGetData(true,
                        App.getContext().getString(R.string.message_success));
                else listener.onGetData(false,
                        App.getContext().getString(R.string.message_unknown_error));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }
}
