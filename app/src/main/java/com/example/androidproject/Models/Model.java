package com.example.androidproject.Models;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public final static Model instance = new Model();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<List<User>> allUsers = AppLocalDB.db.userDao().getAll();
    public LiveData<List<Post>> allPosts = AppLocalDB.db.postDao().getAll();
    public LiveData<List<Comment>> allComments = AppLocalDB.db.commentDao().getAll();

    public MutableLiveData<LoadingState> usersLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> postsLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> commentsLoadingState = new MutableLiveData<>(LoadingState.loaded);

    private Model() {
    }

    public User getUserByUsername(String username) {
        return ModelFirebase.getUserByUsername(username);
    }

    public static void signOutUser() {
        ModelFirebase.signOutUser();
    }

    public static void signUpUser(String username, String password, ModelFirebase.onAuthenticationResult onComplete) {
        ModelFirebase.signUpUser(username, password, onComplete);
    }

    public static void signInUser(String username, String password, ModelFirebase.onAuthenticationResult onComplete) {
        ModelFirebase.signInUser(username, password, onComplete);
    }

    public LiveData<List<User>> getAllUsers() {
        usersLoadingState.setValue(LoadingState.loading);
        Long localLastUpdated = User.getLocal_lastUpdated();

        ModelFirebase.getAllUsers(localLastUpdated, users ->
        {
            executorService.execute(() -> {
                Long lastUpdate = 0L;
                for (User user : users) {
                    AppLocalDB.db.userDao().insertAll(user);
                    if (lastUpdate < user.getLastUpdated())
                        lastUpdate = user.getLastUpdated();
                }
                User.setLocal_lastUpdated(lastUpdate);
                usersLoadingState.postValue(LoadingState.loaded);
            });
        });

        return allUsers;
    }

    public void saveUser(User user, OnCompleteListener listener) {
        usersLoadingState.setValue(LoadingState.loading);
        ModelFirebase.saveUser(user, () -> {
            getAllUsers();
            listener.onComplete();
        });
    }

    public LiveData<List<Post>> getAllPosts() {
        postsLoadingState.setValue(LoadingState.loading);
        Long localLastUpdated = Post.getLocal_lastUpdated();

        ModelFirebase.getAllPosts(localLastUpdated, posts ->
        {
            executorService.execute(() -> {
                Long lastUpdate = 0L;
                for (Post post : posts) {
                    AppLocalDB.db.postDao().insertAll(post);
                    if (lastUpdate < post.getLastUpdated())
                        lastUpdate = post.getLastUpdated();
                }
                Post.setLocal_lastUpdated(lastUpdate);
                postsLoadingState.postValue(LoadingState.loaded);
            });
        });

        return allPosts;
    }

    public void savePost(Post post, OnCompleteListener listener) {
        postsLoadingState.setValue(LoadingState.loading);
        ModelFirebase.savePost(post, () -> {
            getAllPosts();
            listener.onComplete();
        });
    }

    public LiveData<List<Comment>> getAllComments() {
        commentsLoadingState.setValue(LoadingState.loading);
        Long localLastUpdated = Comment.getLocal_lastUpdated();

        ModelFirebase.getAllComments(localLastUpdated, comments ->
        {
            executorService.execute(() -> {
                Long lastUpdate = 0L;
                for (Comment comment : comments) {
                    AppLocalDB.db.commentDao().insertAll(comment);
                    if (lastUpdate < comment.getLastUpdated())
                        lastUpdate = comment.getLastUpdated();
                }
                Comment.setLocal_lastUpdated(lastUpdate);
                commentsLoadingState.postValue(LoadingState.loaded);
            });
        });
        return allComments;
    }

    public void saveComment(Comment comment, OnCompleteListener listener) {
        commentsLoadingState.setValue(LoadingState.loading);
        ModelFirebase.saveComment(comment, () -> {
            getAllComments();
            listener.onComplete();
        });
    }

    public enum LoadingState {
        loaded,
        loading,
        error
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    public interface UploadImageListener {
        void onComplete(String url);
    }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        ModelFirebase.uploadImage(imageBmp, name, listener);
    }

}
