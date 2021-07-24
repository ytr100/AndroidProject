package com.example.androidproject.Models;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


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

    public interface onAuthenticationResult {
        public void execute(String email);
    }

    public interface GetUserWithEmailListener {
        public void onComplete(User user);
    }
    public void getUserByEmail(String email, GetUserWithEmailListener listener) {
        ModelFirebase.getUserByEmail(email, listener);
    }

    public static void signOutUser() {
        ModelFirebase.signOutUser();
    }

    public void signUpUser(String email, String password, onAuthenticationResult onComplete, onAuthenticationResult onError) {
        ModelFirebase.signUpUser(email, password, onComplete, onError);
    }

    public void signInUser(String email, String password, onAuthenticationResult onComplete, onAuthenticationResult onError) {
        ModelFirebase.signInUser(email, password, onComplete, onError);
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

    public void deleteUser(User user, OnCompleteListener listener) {
        usersLoadingState.setValue(LoadingState.loading);
        ModelFirebase.deleteUser(user, () -> {
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

    public LiveData<List<Post>> getPostsFromUser(User user) {
        postsLoadingState.setValue(LoadingState.loading);
        Long localLastUpdated = Post.getLocal_lastUpdated();

        ModelFirebase.getAllPostFromUser(user, localLastUpdated, posts ->
                executorService.execute(() -> {
                    Long lastUpdate = 0L;
                    for (Post post : posts) {
                        AppLocalDB.db.postDao().insertAll(post);
                        if (lastUpdate < post.getLastUpdated())
                            lastUpdate = post.getLastUpdated();
                    }
                    Post.setLocal_lastUpdated(lastUpdate);
                    postsLoadingState.postValue(LoadingState.loaded);
                }));

        return allPosts;
    }

    public void savePost(Post post, OnCompleteListener listener) {
        postsLoadingState.setValue(LoadingState.loading);
        ModelFirebase.savePost(post, () -> {
            getAllPosts();
            listener.onComplete();
        });
    }

    public void deletePost(Post post, OnCompleteListener listener) {
        usersLoadingState.setValue(LoadingState.loading);
        ModelFirebase.deletePost(post, () -> {
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

    public LiveData<List<Comment>> getAllCommentsFromPost(Post post) {
        commentsLoadingState.setValue(LoadingState.loading);
        Long localLastUpdated = Comment.getLocal_lastUpdated();

        ModelFirebase.getCommentsFromPost(post, localLastUpdated, comments ->
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

    public LiveData<List<Comment>> getAllCommentsFromParentComment(Comment parentComment) {
        commentsLoadingState.setValue(LoadingState.loading);
        Long localLastUpdated = Comment.getLocal_lastUpdated();

        ModelFirebase.getCommentsFromParentComment(parentComment, localLastUpdated, comments ->
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

    public void deleteComment(Comment comment, OnCompleteListener listener) {
        commentsLoadingState.setValue(LoadingState.loading);
        ModelFirebase.deleteComment(comment, () -> {
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
