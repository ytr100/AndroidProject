package com.example.androidproject.UI.MainApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.androidproject.Model.Entity.User;
import com.example.androidproject.R;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private String username;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        setHasOptionsMenu(true);
        username = ProfileFragmentArgs.fromBundle(getArguments()).getUsername();
        TextView userText = root.findViewById(R.id.profile_username);
        TextView email = root.findViewById(R.id.profile_email);
        User user = profileViewModel.getUser(username);
        userText.setText(user.getUsername());
        email.setText(user.getEmail());
        return root;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull
            MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_profile_posts:
                ProfileFragmentDirections.ActionProfileFragmentToPostsFragment action
                        = ProfileFragmentDirections.actionProfileFragmentToPostsFragment().setUsername(username);
                Navigation.findNavController(requireView()).navigate(action);
                return true;
            case R.id.menu_profile_comments:
                ProfileFragmentDirections.ActionProfileFragmentToCommentsFragment action2
                        = ProfileFragmentDirections.actionProfileFragmentToCommentsFragment(null,null).setUsername(username);
                Navigation.findNavController(requireView()).navigate(action2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}