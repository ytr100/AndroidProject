package com.example.androidproject.UI.MainApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ProfileViewModel profileViewModel;
    private String username;
    private Bitmap imageBitmap;
    private ImageView photo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        setHasOptionsMenu(true);

        username = ProfileFragmentArgs.fromBundle(getArguments()).getUsername();
        TextView userText = root.findViewById(R.id.profile_username);
        TextView email = root.findViewById(R.id.profile_email);
        photo = root.findViewById(R.id.profile_photo);
        ImageButton takePic = root.findViewById(R.id.profile_picture_btn);
        ProgressBar progressBar = root.findViewById(R.id.profile_progressBar);

        if (username.equals(MyModel.CURRENT_USER)) {//TODO: auth
            takePic.setOnClickListener(v -> {
                dispatchTakePictureIntent();
                if (imageBitmap != null) {
                    takePic.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    MyModel.instance.uploadImage(imageBitmap, username, url -> {//TODO: auth
                        profileViewModel.getUser(username, user ->
                                profileViewModel.editUser(user, url, () -> {
                            takePic.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                        }));
                    });
                }

            });
        } else takePic.setVisibility(View.GONE);


        progressBar.setVisibility(View.VISIBLE);
        profileViewModel.getUser(username, user -> {
            userText.setText(user.getUsername());
            email.setText(user.getEmail());

            photo.setImageResource(R.drawable.ic_launcher_background);
            if (user.getPhoto() != null && !user.getPhoto().equals("")) {
                Picasso.get().load(user.getPhoto()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(photo);
            }
            progressBar.setVisibility(View.GONE);
        });
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
        switch (item.getItemId()) {
            case R.id.menu_profile_posts:
                ProfileFragmentDirections.ActionProfileFragmentToPostsFragment action
                        = ProfileFragmentDirections.actionProfileFragmentToPostsFragment().setUsername(username);
                Navigation.findNavController(requireView()).navigate(action);
                return true;
            case R.id.menu_profile_comments:
                ProfileFragmentDirections.ActionProfileFragmentToCommentsFragment action2
                        = ProfileFragmentDirections.actionProfileFragmentToCommentsFragment(null, null).setUsername(username);
                Navigation.findNavController(requireView()).navigate(action2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
        }
    }


}