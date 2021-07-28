package com.example.androidproject.UI.Auth;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Listeners.OnAuthenticationResult;

public class LoginViewModel extends ViewModel {
    public void signIn(String email, String password, OnAuthenticationResult onComplete, OnAuthenticationResult onError) {
        MyModel.instance.signInUser(email, password, email1 ->
                MyModel.instance.getUserByEmail(email1, user -> {
                    MyModel.CURRENT_USER = user;
                    onComplete.execute(email1);
                }), onError);
    }
}
