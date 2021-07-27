package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Entity.User;

public class EditViewModel extends ViewModel {

    public void insertPost(Post p, String username){
        MyModel.instance.insertPost(p,username);
    }
    public void editPost(Post p){
        MyModel.instance.editPost(p);
    }
    public User getCurrentUser(){
        return MyModel.instance.getByUserName(MyModel.CURRENT_USER);
    }
    public Post getPost(String postID){
        return MyModel.instance.getByID(postID);
    }
}