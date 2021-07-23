package com.example.androidproject.UI.Auth;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Models.Model;
import com.example.androidproject.Models.User;

public class SignUpViewModel extends ViewModel {
    public void signUp(String username, String email, String password, Model.onAuthenticationResult onComplete, Model.onAuthenticationResult onError) {
        Model.instance.signUpUser(email, password, onComplete, onError);
        User user = User.createUser(username, email);
        Model.instance.saveUser(user, () -> {});
    }
}
