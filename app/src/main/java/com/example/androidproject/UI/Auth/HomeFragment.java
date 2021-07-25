package com.example.androidproject.UI.Auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidproject.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btn = root.findViewById(R.id.home_signup_btn);
        btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_HomeFragment_to_signup));
        Button btn2 = root.findViewById(R.id.home_login_btn);
        btn2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_HomeFragment_to_login));
        return root;
    }
}