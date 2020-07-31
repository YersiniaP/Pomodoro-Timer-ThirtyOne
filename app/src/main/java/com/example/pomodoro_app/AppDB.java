package com.example.pomodoro_app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Users.class}, version = 1)
public abstract class AppDB extends RoomDatabase{
    public abstract Users_Dao users_dao();
}
