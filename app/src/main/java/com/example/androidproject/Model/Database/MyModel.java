package com.example.androidproject.Model.Database;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidproject.Model.Database.Local.LocalDataBase;
import com.example.androidproject.Model.Database.Remote.MyModelFirebase;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Message;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;
import com.example.androidproject.Model.Listeners.GetCommentListener;
import com.example.androidproject.Model.Listeners.GetPostListener;
import com.example.androidproject.Model.Listeners.GetUserListener;
import com.example.androidproject.Model.Listeners.OnCommentsCompleteListener;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;
import com.example.androidproject.Model.Listeners.OnPostsCompleteListener;
import com.example.androidproject.Model.Listeners.UploadImageListener;
import com.example.androidproject.MyApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel {


    public static final MyModel instance = new MyModel();
    public static final String NEXT_COMMENT_ID = "8";//TODO: auto generate ids
    public static String CURRENT_USER = "C";//TODO: authentication
    public static String NEXT_POST_ID = "4";//TODO: auto generate ids
    public final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final LocalDataBase local;
    public LiveData<List<User>> allUsers;
    public LiveData<List<Post>> allPosts;
    public LiveData<List<Comment>> allComments;
    public MutableLiveData<UserLoadingState> usersLoadingState;
    public MutableLiveData<PostLoadingState> postsLoadingState;
    public MutableLiveData<CommentLoadingState> commentsLoadingState;

    private MyModel() {
        usersLoadingState = new MutableLiveData<>(UserLoadingState.loading);
        postsLoadingState = new MutableLiveData<>(PostLoadingState.loading);
        commentsLoadingState = new MutableLiveData<>(CommentLoadingState.loading);
        local = new LocalDataBase();
        allPosts = local.getAllPosts();
        allComments = local.getAllComments();
        allUsers = local.getAllUsers();
        allPosts.observeForever(posts -> postsLoadingState.setValue(PostLoadingState.loaded));
        allComments.observeForever(comments -> commentsLoadingState.setValue(CommentLoadingState.loaded));
        allUsers.observeForever(users -> usersLoadingState.setValue(UserLoadingState.loaded));
    }

    public void insertPost(Message m, String username, GetPostListener actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        executor.execute(() ->
                local.insertPost(m, username, post ->  MyApplication.mainHandler.post(()-> actionComplete.onComplete(post))));
    }

    public void editPost(Post p, OnDBActionComplete actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        executor.execute(() -> {
            local.editPost(p);
            MyApplication.mainHandler.post(actionComplete::onComplete);
        });

    }

    public void deletePost(Post p, OnDBActionComplete actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        executor.execute(() -> {
            local.deletePost(p);
            MyApplication.mainHandler.post(actionComplete::onComplete);
        });
    }

    public void deleteComment(Comment c, OnDBActionComplete actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        executor.execute(() -> {
            local.deleteComment(c);
            MyApplication.mainHandler.post(actionComplete::onComplete);
        });
    }

    public void editComment(Comment c, OnDBActionComplete actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        executor.execute(() -> {
            local.editComment(c);
            MyApplication.mainHandler.post(actionComplete::onComplete);
        });
    }

    public void insertComment(Message m, String username, String postID, String parentCommentID, GetCommentListener actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        executor.execute(() ->
                local.insertComment(m, username, postID, parentCommentID, comment ->
                MyApplication.mainHandler.post(()-> actionComplete.onComplete(comment))));
    }

    public void getPostByID(String postID, GetPostListener listener) {//
        executor.execute(() -> {
            Post p = local.getPostByID(postID);
            MyApplication.mainHandler.post(() -> listener.onComplete(p));
        });

    }

    public void incrementPostID() {
        int x = Integer.parseInt(NEXT_POST_ID) + 1;
        NEXT_POST_ID = Integer.toString(x);
    }

    public void incrementCommentID() {
        int x = Integer.parseInt(NEXT_COMMENT_ID) + 1;
        NEXT_POST_ID = Integer.toString(x);
    }

    public void getCommentsOfPost(String postID, OnCommentsCompleteListener actionComplete) {
        executor.execute(() -> {
          List<Comment> result =  local.getCommentsOfPost(postID);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public void getCommentsOfComment(String parentCommentID, OnCommentsCompleteListener actionComplete) {

        executor.execute(() -> {
            List<Comment> result =  local.getCommentsOfComment(parentCommentID);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public void getCommentsOfUser(String username, OnCommentsCompleteListener actionComplete) {
        executor.execute(() -> {
            List<Comment> result =  local.getCommentsOfUser(username);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public LiveData<List<Post>> getAllPosts() {

        return allPosts;
    }

    public void getPostsOfUser(String username, OnPostsCompleteListener actionComplete) {
        executor.execute(() -> {
            List<Post> result =  local.getPostsOfUser(username);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public void getUserByID(String username, GetUserListener listener) {
        executor.execute(() -> {
            User u = local.getByUserName(username);
            MyApplication.mainHandler.post(() -> listener.onComplete(u));
        });
    }

    public void getCommentByID(String commentID, GetCommentListener listener) {
        executor.execute(() -> {
            Comment c = local.getCommentByID(commentID);
            MyApplication.mainHandler.post(() -> listener.onComplete(c));
        });
    }

    public enum UserLoadingState {
        loading,
        loaded
    }

    public enum PostLoadingState {
        loading,
        loaded
    }

    public enum CommentLoadingState {
        loading,
        loaded
    }
    public  void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener){
        MyModelFirebase.uploadImage(imageBmp,name,listener);
    }
    public  void editUser(User user, String newPhoto, OnDBActionComplete actionComplete){
        //TODO: implement
    }
}
