package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Entity.User;

public class ProfileViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private User currentUser;
    public ProfileViewModel(){
        //get currentUser from the model
    }
    public User getUser(){
        return currentUser;
    }
}