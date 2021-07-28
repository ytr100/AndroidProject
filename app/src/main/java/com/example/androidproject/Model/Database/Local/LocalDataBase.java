package com.example.androidproject.Model.Database.Local;

import androidx.lifecycle.LiveData;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;

import java.util.List;

public class LocalDataBase {//every method except LiveData must be called from a background thread


    public void savePost(Post p) {

        LocalDB.db.postDao().insertAllPosts(p);
    }


    public void deletePost(Post p) {
        LocalDB.db.postDao().delete(p);

    }

    public void saveUser(User u) {
        LocalDB.db.userDao().insertAllUsers(u);
    }

    public void deleteUser(User u) {
        LocalDB.db.userDao().delete(u);
    }

    public void saveComment(Comment c) {

        LocalDB.db.commentDao().insertAllComments(c);
    }


    public void deleteComment(Comment c) {
        LocalDB.db.commentDao().delete(c);

    }


    public Post getPostByID(String postID) {

        return LocalDB.db.postDao().getPost(postID);
    }


    public User getByUserName(String username) {

        return LocalDB.db.userDao().getUser(username);
    }


    public Comment getCommentByID(String commentID) {
        return LocalDB.db.commentDao().getComment(commentID);
    }


    public List<Comment> getCommentsOfPost(String postID) {

        return LocalDB.db.commentDao().getCommentsOfPost(postID);
    }


    public List<Comment> getCommentsOfComment(String parentCommentID) {
        return LocalDB.db.commentDao().getCommentsOfComment(parentCommentID);
    }


    public List<Comment> getCommentsOfUser(String username) {
        return LocalDB.db.commentDao().getCommentsOfUser(username);
    }


    public LiveData<List<Post>> getAllPosts() {

        return LocalDB.db.postDao().getAllPosts();
    }


    public LiveData<List<User>> getAllUsers() {
        return LocalDB.db.userDao().getAllUsers();
    }


    public LiveData<List<Comment>> getAllComments() {
        return LocalDB.db.commentDao().getAllComments();
    }


    public List<Post> getPostsOfUser(String username) {
        return LocalDB.db.postDao().getPostsFromUser(username);
    }
}
