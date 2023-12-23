package com.raytalktech.scanerme.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.raytalktech.scanerme.data.source.local.entity.UserEntity;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDataLogin(UserEntity userEntity);

    @Query("SELECT * FROM userEntity WHERE username is :username")
    LiveData<UserEntity> getUserData(String username);

}
