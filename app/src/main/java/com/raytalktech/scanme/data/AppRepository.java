package com.raytalktech.scanme.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.raytalktech.scanme.data.source.local.LocalDataSource;
import com.raytalktech.scanme.data.source.local.entity.UserEntity;
import com.raytalktech.scanme.data.source.remote.ApiResponse;
import com.raytalktech.scanme.data.source.remote.RemoteDataSource;
import com.raytalktech.scanme.utils.AppExecutors;
import com.raytalktech.scanme.vo.Resource;

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
    public LiveData<Resource<UserEntity>> checkLoginStatus() {
        return null;
    }

    @Override
    public LiveData<Resource<UserEntity>> getUserLoginStatus(String username) {
        return null;
    }

    @Override
    public LiveData<ApiResponse<BaseResponse.ResultData>> qrResultData(String url) {
        return remoteDataSource.getResultData(url);
    }
}
