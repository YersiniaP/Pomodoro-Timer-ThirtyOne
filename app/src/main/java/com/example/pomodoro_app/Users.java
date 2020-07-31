package com.example.pomodoro_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Users {
    @PrimaryKey(autoGenerate = true)
    public int user_id = 0;

    @ColumnInfo(name = "db_email")
    public String db_email;

    @ColumnInfo(name = "db_username")
    public String db_username;

    @ColumnInfo(name = "db_password")
    public String db_password;

    @ColumnInfo(name = "db_level")
    public Integer db_level;

    @ColumnInfo(name = "db_xp")
    public Integer db_xp;
}
