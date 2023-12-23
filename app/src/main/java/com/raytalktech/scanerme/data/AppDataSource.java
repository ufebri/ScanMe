package com.raytalktech.scanerme.data;


import androidx.lifecycle.LiveData;

import com.raytalktech.scanerme.data.source.remote.ApiResponse;

import okhttp3.RequestBody;

public interface AppDataSource {

    LiveData<ApiResponse<BaseResponse.ResultLogin>> loginResult(RequestBody requestBody);

    LiveData<ApiResponse<BaseResponse.ResultCheckIn>> checkInResult(RequestBody requestBody, String token);
}
