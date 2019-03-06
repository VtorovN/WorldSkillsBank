package com.example.worldskills.Repository;

import com.example.worldskills.API.ProfileApi;
import com.example.worldskills.Listener.HistoryListener;
import com.example.worldskills.Model.Card;
import com.example.worldskills.Model.NewCardName;
import com.example.worldskills.Model.Operation;
import com.example.worldskills.Utility.App;
import com.example.worldskills.Listener.DataListener;
import com.example.worldskills.Model.LoginData;
import com.example.worldskills.Model.NewLoginData;
import com.example.worldskills.R;
import com.example.worldskills.Model.Token;
import com.example.worldskills.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryProfile {

    private static Retrofit retrofit;
    private static ProfileApi profileApi;

    private static void buildRetrofitAndApi() {
        if (retrofit == null || profileApi == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://92.63.64.193:10010/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            profileApi = retrofit.create(ProfileApi.class);
        }
    }

    public static void getUserInfo(final DataListener listener) {
        buildRetrofitAndApi();
        Call<User> userInfo = profileApi.getUserInfo(Token.getCurrentToken().getToken());
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
        buildRetrofitAndApi();
        Call<Token> login = profileApi.login(new LoginData(username, password));
        login.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    Token.setCurrentToken(response.body());
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
        buildRetrofitAndApi();
        Call<User> changeLoginData = profileApi.changeLogin(Token.getCurrentToken().getToken(),
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
        buildRetrofitAndApi();
        Call<User> changePasswordData = profileApi.changePassword(Token.getCurrentToken().getToken(),
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
        buildRetrofitAndApi();
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

    public static void changeCardName(final String cardName, String cardNumber,
                                      final DataListener listener) {
        buildRetrofitAndApi();
        Call<Card> changeCardName = profileApi.changeCardName(cardNumber,
                new NewCardName(cardName), Token.getCurrentToken().getToken());
        changeCardName.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.code() == 200) {
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                } else {
                    listener.onGetData(false,
                            App.getContext().getString(R.string.message_unknown_error) );
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }

    public static void blockCard(String cardNumber, final DataListener listener) {
        buildRetrofitAndApi();
        Call<Card> cardBlock = profileApi.blockCard(cardNumber, Token.getCurrentToken().getToken());
        cardBlock.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                if (response.code() == 200) {
                    listener.onGetData(true,
                            App.getContext().getString(R.string.message_success));
                } else {
                    listener.onGetData(false,
                            App.getContext().getString(R.string.message_unknown_error) );
                }
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                listener.onGetData(false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }

    public static void getCardOperationsHistory(String cardNumber, final HistoryListener listener) {
        buildRetrofitAndApi();
        Call<Operation[]> operationsHistory = profileApi.getCardOperationsHistory(cardNumber,
                Token.getCurrentToken().getToken());
        operationsHistory.enqueue(new Callback<Operation[]>() {
            @Override
            public void onResponse(Call<Operation[]> call, Response<Operation[]> response) {
                if (response.code() == 200) {
                    listener.onGetHistory(response.body(),true,
                            App.getContext().getString(R.string.message_success));
                } else {
                    listener.onGetHistory(null,false,
                            App.getContext().getString(R.string.message_unknown_error) );
                }
            }

            @Override
            public void onFailure(Call<Operation[]> call, Throwable t) {
                listener.onGetHistory(null,false,
                        App.getContext().getString(R.string.message_server_error));
            }
        });
    }
}
