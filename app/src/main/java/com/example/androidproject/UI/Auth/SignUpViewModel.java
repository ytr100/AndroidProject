package com.example.androidproject.UI.Auth;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Models.Model;
import com.example.androidproject.Models.ModelFirebase;

public class SignUpViewModel extends ViewModel {
    public void signUp(String email, String password, ModelFirebase.onAuthenticationResult onComplete, ModelFirebase.onAuthenticationResult onError) {
        Model.instance.signUpUser(email,password,onComplete,onError);
    }
}
