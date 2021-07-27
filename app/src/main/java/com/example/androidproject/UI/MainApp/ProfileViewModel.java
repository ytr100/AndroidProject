package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.User;

public class ProfileViewModel extends ViewModel {
    private User user;
    public ProfileViewModel(){
        //get user from the model
    }
    public User getUser(String username){
        user = MyModel.instance.getByUserName(username);
        return user;
    }
    public User getUser(){
        return user;
    }
    public User getCurrentUser(){
        return getUser(MyModel.CURRENT_USER);
    }
}