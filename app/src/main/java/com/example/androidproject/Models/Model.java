package com.example.androidproject.Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public final static Model instance = new Model();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MutableLiveData<List<User>> allUsers = new MutableLiveData<>(new LinkedList<>());
    public MutableLiveData<List<Post>> allPosts = new MutableLiveData<>(new LinkedList<>());
    public MutableLiveData<List<Comment>> allComments = new MutableLiveData<>(new LinkedList<>());

    public MutableLiveData<LoadingState> usersLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> postsLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> commentsLoadingState = new MutableLiveData<>(LoadingState.loaded);

    private Model() {
    }

    public LiveData<List<User>> getAllUsers() {
        usersLoadingState.setValue(LoadingState.loading);
        //...
        return allUsers;
    }

    public void saveUser(User user, OnCompleteListener listener) {
        usersLoadingState.setValue(LoadingState.loading);
        //...
    }

    public LiveData<List<Post>> getAllPosts() {
        postsLoadingState.setValue(LoadingState.loading);
        //...
        return allPosts;
    }

    public void savePost(Post post, OnCompleteListener listener) {
        postsLoadingState.setValue(LoadingState.loading);
        //...
    }

    public LiveData<List<Comment>> getAllComments() {
        commentsLoadingState.setValue(LoadingState.loading);
        //...
        return allComments;
    }

    public void saveComment(Comment comment, OnCompleteListener listener) {
        commentsLoadingState.setValue(LoadingState.loading);
        //...
    }

    public enum LoadingState {
        loaded,
        loading,
        error
    }

    public interface OnCompleteListener {
        void onComplete();
    }


}
