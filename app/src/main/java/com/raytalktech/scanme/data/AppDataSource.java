package com.raytalktech.scanme.data;


import androidx.lifecycle.LiveData;

import com.raytalktech.scanme.data.source.local.entity.UserEntity;
import com.raytalktech.scanme.data.source.remote.ApiResponse;
import com.raytalktech.scanme.vo.Resource;

import okhttp3.RequestBody;

public interface AppDataSource {

    LiveData<ApiResponse<BaseResponse.ResultLogin>> loginResult(RequestBody requestBody);

    LiveData<ApiResponse<BaseResponse.ResultCheckIn>> checkInResult(RequestBody requestBody, String token);
}
