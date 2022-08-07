package com.raytalktech.scanme.di;

import android.content.Context;

import com.raytalktech.scanme.data.AppRepository;
import com.raytalktech.scanme.data.source.local.LocalDataSource;
import com.raytalktech.scanme.data.source.local.room.AppDatabase;
import com.raytalktech.scanme.data.source.remote.RemoteDataSource;
import com.raytalktech.scanme.utils.AppExecutors;

public class Injection {

    public static AppRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.appDao());
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        AppExecutors appExecutors = new AppExecutors();
        return AppRepository.getInstance(remoteDataSource, localDataSource, appExecutors);
    }
}
