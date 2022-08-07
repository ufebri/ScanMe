package com.raytalktech.scanme.data.source.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userEntity")
public class UserEntity {

    @NonNull
    @ColumnInfo(name = "username")
    private final String userName;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    private final String date;

    @NonNull
    @ColumnInfo(name = "isLogin")
    private final boolean isLogin;


    public UserEntity(@NonNull String userName, @NonNull String date, @NonNull boolean isLogin) {
        this.userName = userName;
        this.date = date;
        this.isLogin = isLogin;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public boolean getIsLogin() {
        return isLogin;
    }
}
