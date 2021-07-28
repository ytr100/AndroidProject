package com.example.androidproject.Model.Database.Local.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.User;

import java.util.List;

@Dao
public interface CommentDao {

    @Query("select * from Comment")
    LiveData<List<Comment>> getAllComments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllComments(Comment... comments);

    @Delete
    void delete(Comment comment);

    @Query("select * from Comment where postID = :postID")
    List<Comment> getCommentsOfPost(String postID);

    @Query("select * from Comment where parentCommentID = :parentCommentID")
    List<Comment> getCommentsOfComment(String parentCommentID);

    @Query("select * from Comment where commentUsername = :username")
    List<Comment> getCommentsOfUser(String username);

    @Query("select * from Comment where commentID =:commentID")
    Comment getComment(String commentID);
}
