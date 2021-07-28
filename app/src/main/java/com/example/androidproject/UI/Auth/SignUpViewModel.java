package com.example.androidproject.UI.Auth;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Listeners.OnAuthenticationResult;

public class SignUpViewModel extends ViewModel {
    public void signUp(String username, String email, String password, OnAuthenticationResult onComplete, OnAuthenticationResult onError) {
        MyModel.instance.signUpUser(username, email, password, email1 ->
                MyModel.instance.insertUser(username, email1, user -> onComplete.execute(email1)), onError);
    }
}
