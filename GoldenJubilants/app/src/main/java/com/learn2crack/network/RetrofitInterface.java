package com.learn2crack.network;

import com.learn2crack.model.Opportunities;
import com.learn2crack.model.Response;
import com.learn2crack.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @POST("users")
    Observable<Response> register(@Body User user);

    @POST("opportunities") //line added by Bilal
    Observable<Response> register(@Body Opportunities opportunity); //line added by Bilal

    ///@GET("opportunities") //line added by Bilal

    @POST("authenticate")
    Observable<Response> login();

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @GET("opportunities/{email}") //line added by Bilal
    Observable<List<Opportunities>> getOpportunities(@Path("email") String email); //lined added by Bilal


    // I was doing this before
    // @GET("opportunities/{email}") //line added by Bilal
    // Observable<Opportunities> getOpportunities(@Path("email") String email); //lined added by Bilal

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}
