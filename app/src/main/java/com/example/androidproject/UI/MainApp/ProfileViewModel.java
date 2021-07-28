package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.User;
import com.example.androidproject.Model.Listeners.GetUserListener;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;

public class ProfileViewModel extends ViewModel {
    public ProfileViewModel() {
    }

    public void getUser(String username, GetUserListener listener) {
        MyModel.instance.getUserByID(username, listener);
    }

    public void editUser(User u, String newPhoto, OnDBActionComplete actionComplete) {
        MyModel.instance.editUser(u, newPhoto, actionComplete);
    }

    public void deleteUser(User u, OnDBActionComplete actionComplete) {
        MyModel.instance.deleteUser(u, actionComplete);
    }

    public User getCurrentUser() {
        return MyModel.CURRENT_USER;
    }
}