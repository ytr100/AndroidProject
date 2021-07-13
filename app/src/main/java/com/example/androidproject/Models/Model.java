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

    private Model() {
    }

    public enum LoadingState {
        loaded,
        loading,
        error
    }

    public MutableLiveData<List<User>> allUsers = new MutableLiveData<>(new LinkedList<>());
    public MutableLiveData<List<Post>> allPosts = new MutableLiveData<>(new LinkedList<>());
    public MutableLiveData<List<Comment>> allComments = new MutableLiveData<>(new LinkedList<>());

    public MutableLiveData<LoadingState> users_loadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> posts_loadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> comments_loadingState = new MutableLiveData<>(LoadingState.loaded);

    public interface OnCompleteListener {
        void onComplete();
    }

    public LiveData<List<User>> getAllUsers() {
        users_loadingState.setValue(LoadingState.loading);
        //...
    }

    public void saveUser(User student, OnCompleteListener listener) {
        users_loadingState.setValue(LoadingState.loading);
        //...
    }

    public LiveData<List<User>> getAllPosts() {
        posts_loadingState.setValue(LoadingState.loading);
        //...
    }


    public void savePost(User student, OnCompleteListener listener) {
        posts_loadingState.setValue(LoadingState.loading);
        //...
    }

    public LiveData<List<User>> getAllComments() {
        comments_loadingState.setValue(LoadingState.loading);
        //...
    }


    public void saveComment(User student, OnCompleteListener listener) {
        comments_loadingState.setValue(LoadingState.loading);
        //...
    }


}
