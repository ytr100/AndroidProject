package com.example.androidproject.UI.Auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.androidproject.R;

import java.util.List;

public class login extends Fragment {
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        EditText email = root.findViewById(R.id.login_email);
        EditText password = root.findViewById(R.id.login_password);
        Button btn = root.findViewById(R.id.login_btn);
        ProgressBar progressBar = root.findViewById(R.id.login_progressBar);//TODO: implement
        btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_login_to_appActivity));//TODO: auth

//        Model.instance.getUserByEmail("tamir@gmail.com", (user) -> {
//            LiveData<List<User>> allList = Model.instance.getAllUsers();
////                                LiveData<List<Post>> list = Model.instance.getPostsFromUser(user);
//            allList.observe(getViewLifecycleOwner(), allPosts -> {
//                for (User p : allPosts)
//                    Log.d("TAG", p.getUsername() + " : " + " by " + p.getLastUpdated() + " !!!");
//            });
//        });
        return root;
    }
}