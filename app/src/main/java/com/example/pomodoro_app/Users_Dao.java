package com.example.pomodoro_app;

import android.text.Editable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Users_Dao {
    @Query(value = "SELECT * FROM Users")
    List<Users> get_all_users();

    @Query(value = "SELECT * FROM Users WHERE db_email = :target_email")
    Users get_user_by_email(String target_email);

    @Query(value = "SELECT * FROM Users WHERE db_username = :target_username")
    Users get_user_by_username(String target_username);

    @Insert
    void insert_user(Users... new_user);

    @Delete
    void delete(Users user);
}
