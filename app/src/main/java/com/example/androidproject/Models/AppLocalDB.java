package com.example.androidproject.Models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidproject.MyApplication;

@Database(entities = {User.class, Post.class, Comment.class}, version = 5)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract PostDao postDao();

    public abstract CommentDao commentDao();
}

public class AppLocalDB {
    final static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

