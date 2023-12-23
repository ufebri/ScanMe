package com.raytalktech.scanerme.di;

import android.content.Context;

import com.raytalktech.scanerme.data.AppRepository;
import com.raytalktech.scanerme.data.source.local.LocalDataSource;
import com.raytalktech.scanerme.data.source.local.room.AppDatabase;
import com.raytalktech.scanerme.data.source.remote.RemoteDataSource;
import com.raytalktech.scanerme.utils.AppExecutors;

public class Injection {

    public static AppRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.appDao());
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        AppExecutors appExecutors = new AppExecutors();
        return AppRepository.getInstance(remoteDataSource, localDataSource, appExecutors);
    }
}
