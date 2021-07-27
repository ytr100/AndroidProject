package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//TODO: livedata
public class PostsViewModel extends ViewModel {
    public LiveData<List<Post>> data;

    public PostsViewModel() {
        data = new MutableLiveData<>(new ArrayList<>());
    }
    public LiveData<List<Post>> getAllPosts(){
        data = MyModel.instance.getAllPosts();
        return data;
    }
    public LiveData<List<Post>> getPostsOfUser(String username){
        data = MyModel.instance.getPostsOfUser(username);
        return data;
    }
    public void deletePost(Post p){
        MyModel.instance.deletePost(MyModel.instance.getByID(p.getPostID()));
    }


}
