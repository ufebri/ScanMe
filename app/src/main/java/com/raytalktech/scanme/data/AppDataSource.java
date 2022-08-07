package com.raytalktech.scanme.data;


import androidx.lifecycle.LiveData;

import com.raytalktech.scanme.data.source.local.entity.UserEntity;
import com.raytalktech.scanme.data.source.remote.ApiResponse;
import com.raytalktech.scanme.vo.Resource;

public interface AppDataSource {

    LiveData<Resource<UserEntity>> checkLoginStatus();

    LiveData<Resource<UserEntity>> getUserLoginStatus(String username);

    LiveData<ApiResponse<BaseResponse.ResultData>> qrResultData(String url);
}
