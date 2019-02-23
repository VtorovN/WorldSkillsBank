package com.example.worldskills;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryProfile {

    public static void getUserInfo(final DataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<User> userInfo = profileApi.getUserInfo(Token.getCurrentToken());
        userInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User.setCurrentUser(response.body());
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                } else {
                    listener.onGetData(false,
                            App.getContext().getString(R.string.message_unknown_error));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }

    //login
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
        Call<User> changeLoginData = profileApi.changeLogin(Token.getCurrentToken(),
                new NewLoginData(newLogin, null));
        changeLoginData.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                    User.setCurrentUser(response.body());
                }
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
        Call<User> changePasswordData = profileApi.changePassword(Token.getCurrentToken(),
                new NewLoginData(null, newPassword));
        changePasswordData.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                    User.setCurrentUser(response.body());
                }
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

    public static void logout(final DataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://92.63.64.193:10010/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProfileApi profileApi = retrofit.create(ProfileApi.class);
        Call<ResponseBody> logout = profileApi.logout(Token.getCurrentToken());
        logout.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                } else {
                    listener.onGetData(false,
                            App.getContext().getString(R.string.message_unknown_error) );
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }
}
