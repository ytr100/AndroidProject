package com.example.androidproject.Model.Database;

import android.graphics.Bitmap;
import android.util.Log;

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
import com.example.androidproject.Model.Listeners.OnAuthenticationResult;
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
    public static User CURRENT_USER = null;
    public final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final LocalDataBase local;
    public LiveData<List<User>> allUsers;
    public LiveData<List<Post>> allPosts;
    public LiveData<List<Comment>> allComments;
    public MutableLiveData<UserLoadingState> usersLoadingState;
    public MutableLiveData<PostLoadingState> postsLoadingState;
    public MutableLiveData<CommentLoadingState> commentsLoadingState;

    private MyModel() {
        usersLoadingState = new MutableLiveData<>(UserLoadingState.loaded);
        postsLoadingState = new MutableLiveData<>(PostLoadingState.loaded);
        commentsLoadingState = new MutableLiveData<>(CommentLoadingState.loaded);
        commentsLoadingState.observeForever(comments ->
                Log.d("TAG3", "changed to: " + commentsLoadingState.getValue()));
        local = new LocalDataBase();
        allPosts = local.getAllPosts();
        allComments = local.getAllComments();
        allUsers = local.getAllUsers();


    }


    public void getUserByEmail(String email, GetUserListener actionComplete) {
        MyModelFirebase.getUserByEmail(email, actionComplete);
    }

    public void signOutUser() {
        MyModelFirebase.signOutUser();
    }

    public void signUpUser(String username, String email, String password, OnAuthenticationResult actionComplete, OnAuthenticationResult onError) {

        MyModelFirebase.signUpUser(username, email, password, actionComplete, onError);
    }

    public void signInUser(String email, String password, OnAuthenticationResult actionComplete, OnAuthenticationResult onError) {
        MyModelFirebase.signInUser(email, password, actionComplete, onError);
    }

    public void insertPost(Message m, String username, GetPostListener actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        MyModelFirebase.insertPost(m, username, post ->
                getPostsFromRemote(() -> actionComplete.onComplete(post)));
    }

    public void insertUser(String username, String email, GetUserListener actionComplete) {
        usersLoadingState.setValue(UserLoadingState.loading);
        MyModelFirebase.insertUser(username, email, user ->
                getUsersFromRemote(() -> actionComplete.onComplete(user)));

    }

    public void editPost(Post p, OnDBActionComplete actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        MyModelFirebase.editPost(p, () -> getPostsFromRemote(actionComplete));
    }

    public void deletePost(Post p, OnDBActionComplete actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        MyModelFirebase.deletePost(p, () -> getPostsFromRemote(actionComplete));
    }

    public void deleteUser(User u, OnDBActionComplete actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        MyModelFirebase.deleteUser(u, () -> getUsersFromRemote(actionComplete));
    }

    public void deleteComment(Comment c, OnDBActionComplete actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        MyModelFirebase.deleteComment(c, () -> getCommentsFromRemote(actionComplete));
    }

    public void editComment(Comment c, OnDBActionComplete actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        MyModelFirebase.editComment(c, () -> getCommentsFromRemote(actionComplete));
    }

    public void insertComment(Message m, String username, String postID, String parentCommentID, GetCommentListener actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        MyModelFirebase.insertComment(m, username, postID, parentCommentID, comment ->
                getCommentsFromRemote(() -> actionComplete.onComplete(comment)));
    }

    public void getPostByID(String postID, GetPostListener listener) {//
        //getPostsFromRemote(() -> {});
        executor.execute(() -> {
            Post p = local.getPostByID(postID);
            MyApplication.mainHandler.post(() -> listener.onComplete(p));
        });

    }

    public void getCommentsOfPost(String postID, OnCommentsCompleteListener actionComplete) {
        executor.execute(() -> {
            List<Comment> result = local.getCommentsOfPost(postID);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public void getCommentsOfComment(String parentCommentID, OnCommentsCompleteListener actionComplete) {
        executor.execute(() -> {
            List<Comment> result = local.getCommentsOfComment(parentCommentID);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public void getCommentsOfUser(String username, OnCommentsCompleteListener actionComplete) {
        executor.execute(() -> {
            List<Comment> result = local.getCommentsOfUser(username);
            MyApplication.mainHandler.post(() -> actionComplete.onComplete(result));
        });
    }

    public LiveData<List<Post>> getAllPosts() {
        getPostsFromRemote(() -> {
        });
        return allPosts;
    }

    public LiveData<List<User>> getAllUsers() {
        getUsersFromRemote(() -> {
        });
        return allUsers;
    }

    public LiveData<List<Comment>> getAllComments() {
        getCommentsFromRemote(() -> {
        });
        return allComments;
    }

    public void getPostsOfUser(String username, OnPostsCompleteListener actionComplete) {
        executor.execute(() -> {
            List<Post> result = local.getPostsOfUser(username);
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

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        MyModelFirebase.uploadImage(imageBmp, name, listener);
    }

    public void editUser(User user, String newPhoto, OnDBActionComplete actionComplete) {
        usersLoadingState.setValue(UserLoadingState.loading);
        MyModelFirebase.editUser(user, newPhoto, () ->
                getUsersFromRemote(actionComplete)
        );
    }

    public void getPostsFromRemote(OnDBActionComplete actionComplete) {
        postsLoadingState.setValue(PostLoadingState.loading);
        Long localLastUpdate = Post.getLocalLastUpdateTime();
        MyModelFirebase.getAllPosts(localLastUpdate, posts ->
                executor.execute(() -> {
                    Long lastUpdate = 0L;
                    for (Post p : posts) {
                        if (p.isDeleted()) {
                            local.deletePost(p);
                        } else {
                            local.savePost(p);
                        }
                        if (lastUpdate < p.getLastUpdated())
                            lastUpdate = p.getLastUpdated();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Post.setLocalLastUpdateTime(lastUpdate);
                    MyApplication.mainHandler.post(() -> {
                        actionComplete.onComplete();
                        postsLoadingState.setValue(PostLoadingState.loaded);
                    });
                }));
    }

    public void getUsersFromRemote(OnDBActionComplete actionComplete) {
        usersLoadingState.setValue(UserLoadingState.loading);
        Long localLastUpdate = User.getLocalLastUpdateTime();
        MyModelFirebase.getAllUsers(localLastUpdate, users ->
                executor.execute(() -> {
                    Long lastUpdate = 0L;
                    for (User user : users) {
                        if (user.isDeleted()) {
                            local.deleteUser(user);
                        } else {
                            local.saveUser(user);
                        }


                        if (lastUpdate < user.getLastUpdated())
                            lastUpdate = user.getLastUpdated();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    User.setLocalLastUpdateTime(lastUpdate);
                    MyApplication.mainHandler.post(() -> {
                        actionComplete.onComplete();
                        usersLoadingState.setValue(UserLoadingState.loaded);
                    });
                }));
    }

    public void getCommentsFromRemote(OnDBActionComplete actionComplete) {
        commentsLoadingState.setValue(CommentLoadingState.loading);
        Long localLastUpdate = Comment.getLocalLastUpdateTime();
        MyModelFirebase.getAllComments(localLastUpdate, comments ->
                executor.execute(() -> {
                    Long lastUpdate = 0L;
                    for (Comment comment : comments) {
                        if (comment.isDeleted()) {
                            local.deleteComment(comment);
                        } else {
                            local.saveComment(comment);
                        }
                        if (lastUpdate < comment.getLastUpdated())
                            lastUpdate = comment.getLastUpdated();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Comment.setLocalLastUpdateTime(lastUpdate);
                    MyApplication.mainHandler.post(() -> {
                        actionComplete.onComplete();
                        commentsLoadingState.setValue(CommentLoadingState.loaded);
                    });

                }));
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
}
