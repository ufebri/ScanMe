package com.raytalktech.scanme.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.raytalktech.scanme.config.AppConfig;
import com.raytalktech.scanme.data.source.local.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppConfig.DB_NAME).build();
            }
        }
        return INSTANCE;
    }
}
