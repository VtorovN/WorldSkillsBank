package com.example.worldskills.API;

import com.example.worldskills.Model.Card;
import com.example.worldskills.Model.LoginData;
import com.example.worldskills.Model.NewCardName;
import com.example.worldskills.Model.NewLoginData;
import com.example.worldskills.Model.Token;
import com.example.worldskills.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProfileApi {
    @POST("login")
    Call<Token> login(@Body LoginData loginData);

    //this doesn't work. "Non-body HTTP method cannot contain @Body"
    //@DELETE("logout")
    //Call<ResponseBody> logout(@Body Token token);

    @HTTP(method = "DELETE", path = "logout", hasBody = true)
    Call<ResponseBody> logout(@Body Token token);

    @GET("user")
    Call<User> getUserInfo(@Query("token") String token);

    @PUT("user/card/{cardNumber}/name")
    Call<Card> changeCardName(@Path("cardNumber") String cardNumber, @Body NewCardName cardName,
                              @Query("token") String token);

    @PUT("user/card/{cardNumber}/block")
    Call<Card> blockCard(@Path("cardNumber") String cardNumber, @Query("token") String token);

    @PATCH("user")
    Call<User> changePassword(@Query("token") String token, @Body NewLoginData newLoginData);

    @PATCH("user")
    Call<User> changeLogin(@Query("token") String token, @Body NewLoginData newLoginData);
}
