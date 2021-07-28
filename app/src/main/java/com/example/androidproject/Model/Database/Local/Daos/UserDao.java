package com.example.androidproject.Model.Database.Local.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidproject.Model.Entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<List<User>> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(User... users);

    @Delete
    void delete(User user);

    @Query("select * from User where username =:username")
    User getUser(String username);
}
