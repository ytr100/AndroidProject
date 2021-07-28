package com.example.androidproject.Model.Database.Local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidproject.Model.Database.Local.Daos.CommentDao;
import com.example.androidproject.Model.Database.Local.Daos.PostDao;
import com.example.androidproject.Model.Database.Local.Daos.UserDao;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;
import com.example.androidproject.MyApplication;

@Database(entities = {User.class, Post.class, Comment.class}, version = 7)
abstract class LocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract PostDao postDao();

    public abstract CommentDao commentDao();
}

public class LocalDB {
    final static public LocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    LocalDbRepository.class,
                    "local.db")
                    .fallbackToDestructiveMigration()
                    .build();
}

