package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;
import com.example.androidproject.Model.Listeners.OnPostsCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//TODO: livedata
public class PostsViewModel extends ViewModel {
    public LiveData<List<Post>> data;
    private List<Post> queryResult;
    public PostsViewModel() {
        data = MyModel.instance.allPosts;
        queryResult = new ArrayList<>();
    }
    public LiveData<List<Post>> getAllPosts(){
        return data;
    }
    public void getPosts(OnPostsCompleteListener actionComplete){
        queryResult = data.getValue();
        actionComplete.onComplete(queryResult);
    }
    public void getPostsOfUser(String username, OnPostsCompleteListener actionComplete){
        MyModel.instance.getPostsOfUser(username, posts -> {
            queryResult = posts;
            actionComplete.onComplete(queryResult);
        });
    }
    public void deletePost(Post p, OnDBActionComplete actionComplete){
        MyModel.instance.deletePost(p,actionComplete);
    }
    public List<Post> getCurrent() {
        if (queryResult != null)
            return queryResult;
        return new ArrayList<>();
    }


    public void refresh(OnDBActionComplete actionComplete) {
        MyModel.instance.getPostsFromRemote(actionComplete);
    }
}
