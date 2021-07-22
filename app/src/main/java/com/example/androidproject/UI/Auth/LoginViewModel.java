package com.example.androidproject.UI.Auth;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Models.Model;
import com.example.androidproject.Models.ModelFirebase;//problem?

public class LoginViewModel extends ViewModel {
    public void signIn(String email, String password, ModelFirebase.onAuthenticationResult onComplete, ModelFirebase.onAuthenticationResult onError) {
        Model.instance.signInUser(email,password,onComplete,onError);
    }
}
