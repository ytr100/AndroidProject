package com.example.androidproject.UI.Auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidproject.R;

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
        btn.setOnClickListener(v ->
//                loginViewModel.signIn(
//                        email.getText().toString(),
//                        password.getText().toString(),
//                        m -> Snackbar.make(view, "parameters are valid", 5 * 1000).show(),
//                        m -> Snackbar.make(view, "invalid parameters (email or password)", 5 * 1000).show()
//                )
                Navigation.findNavController(v).navigate(R.id.action_login_to_appActivity)
        );

        return root;
    }
}