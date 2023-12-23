package com.raytalktech.scanerme.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.raytalktech.scanerme.data.source.local.LocalDataSource;
import com.raytalktech.scanerme.data.source.remote.ApiResponse;
import com.raytalktech.scanerme.data.source.remote.RemoteDataSource;
import com.raytalktech.scanerme.utils.AppExecutors;

import okhttp3.RequestBody;

public class AppRepository implements AppDataSource {

    private volatile static AppRepository INSTANCE = null;

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final AppExecutors appExecutors;

    private AppRepository(@NonNull RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.appExecutors = appExecutors;
    }

    public static AppRepository getInstance(RemoteDataSource remoteDataSource, LocalDataSource localDataSource, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (AppRepository.class) {
                INSTANCE = new AppRepository(remoteDataSource, localDataSource, appExecutors);
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<ApiResponse<BaseResponse.ResultLogin>> loginResult(RequestBody requestBody) {
        return remoteDataSource.getLoginData(requestBody);
    }

    @Override
    public LiveData<ApiResponse<BaseResponse.ResultCheckIn>> checkInResult(RequestBody requestBody, String token) {
        return remoteDataSource.getCheckInData(requestBody, token);
    }
}
