package com.example.androidproject.UI.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;

public class signup extends Fragment {
    private SignUpViewModel signUpViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        EditText username = root.findViewById(R.id.signup_username);
        EditText email = root.findViewById(R.id.signup_email);
        EditText password = root.findViewById(R.id.signup_password);
        Button btn = root.findViewById(R.id.signup_btn);
        ProgressBar progressBar = root.findViewById(R.id.signup_progressBar);
        progressBar.setVisibility(View.GONE);
        btn.setOnClickListener(v -> {
                    btn.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    signUpViewModel.signUp(
                            username.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            mail -> {
                                Snackbar.make(root, "all is good", 5 * 1000).show();
                                progressBar.setVisibility(View.GONE);
                                btn.setEnabled(true);
                                Navigation.findNavController(root).navigate(R.id.action_signup_to_login);
                            },
                            mail -> {
                                Snackbar.make(root, "invalid parameters, maybe email or username are already taken.", 5 * 1000).show();
                                progressBar.setVisibility(View.GONE);
                                btn.setEnabled(true);
                            }
                    );
                }

        );

        return root;
    }

}