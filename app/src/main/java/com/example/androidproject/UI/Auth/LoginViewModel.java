package com.example.androidproject.UI.Auth;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Models.Model;

public class LoginViewModel extends ViewModel {
    public void signIn(String email, String password, Model.onAuthenticationResult onComplete, Model.onAuthenticationResult onError) {
        Model.instance.signInUser(email, password, onComplete, onError);
    }
}
