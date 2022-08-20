package com.raytalktech.scanme.network;


import com.raytalktech.scanme.data.BaseResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    //TODO: #1 Masukin url
    @GET
    Call<BaseResponse> checkResultScan(@Url String url);

    @POST("api/login")
    Call<BaseResponse.ResultLogin> loginResult(@Body RequestBody requestBody);

    @POST("api/checkin")
    Call<BaseResponse.ResultCheckIn> checkInResult(@Body RequestBody requestBody, @Header("Authorization") String auth);
}
