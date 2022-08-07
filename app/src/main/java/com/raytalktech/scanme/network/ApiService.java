package com.raytalktech.scanme.network;


import com.raytalktech.scanme.data.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    //TODO: #1 Masukin url
    @GET
    Call<BaseResponse> checkResultScan(@Url String url);
}
