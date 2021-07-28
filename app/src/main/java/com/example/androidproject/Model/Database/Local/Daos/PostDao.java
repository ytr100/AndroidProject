package com.example.androidproject.Model.Database.Local.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidproject.Model.Entity.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Query("select * from Post")
    LiveData<List<Post>> getAllPosts();
    @Query("select * from Post where postUsername = :username")
    List<Post> getPostsFromUser(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPosts(Post... posts);

    @Delete
    void delete(Post post);

    @Query("select * from Post where postID =:postID")
    Post getPost(String postID);
}
