package com.raytalktech.scanme.data.source.local;


import com.raytalktech.scanme.data.source.local.room.AppDao;

public class LocalDataSource {

    private static LocalDataSource INSTANCE;
    private final AppDao appDao;


    public LocalDataSource(AppDao appDao) {
        this.appDao = appDao;
    }

    public static LocalDataSource getInstance(AppDao appDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(appDao);
        }
        return INSTANCE;
    }

}
