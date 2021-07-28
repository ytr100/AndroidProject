package com.example.androidproject.UI.MainApp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Message;
import com.example.androidproject.R;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditViewModel editViewModel;
    private String postID;
    private String commentID;
    private String parentCommentID;
    private boolean isCreate;
    private Bitmap imageBitmap;
    private ImageView photo;
    private EditText title;
    private EditText content;

//    private void initLoadingState(ProgressBar progressBar) {
//        if (postID == null || (commentID == null && !isCreate)) {//create or edit post
//            MyModel.instance.postsLoadingState.observe(getViewLifecycleOwner(), postLoadingState -> {
//                if (postLoadingState == MyModel.PostLoadingState.loading) {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//            });
//        } else {
//            MyModel.instance.commentsLoadingState.observe(getViewLifecycleOwner(), commentsLoadingState -> {
//                if (commentsLoadingState == MyModel.CommentLoadingState.loading) {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//            });
//        }
//
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        View root = inflater.inflate(R.layout.edit_fragment, container, false);
        setHasOptionsMenu(true);

        title = root.findViewById(R.id.edit_title);
        content = root.findViewById(R.id.edit_content);
        Button save = root.findViewById(R.id.edit_save);
        photo = root.findViewById(R.id.edit_image);
        ImageButton takePic = root.findViewById(R.id.edit_picture_btn);
        takePic.setOnClickListener(v -> dispatchTakePictureIntent());
        ProgressBar progressBar = root.findViewById(R.id.edit_progressBar);
        progressBar.setVisibility(View.GONE);

        EditFragmentArgs args = EditFragmentArgs.fromBundle(getArguments());
        postID = args.getPostID();
        commentID = args.getCommentID();
        parentCommentID = args.getParentCommentID();
        isCreate = args.getIsCreate();
        initUI(title, content, progressBar);

        //initLoadingState(progressBar);// TODO: check if needed

        save.setOnClickListener(v -> {
            save.setEnabled(false);
            takePic.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            if (postID == null) {//create a new post
                createNewPost(v);
            } else if (commentID == null && !isCreate) {//edit post
                editPost(v);
            } else if (commentID != null) {//edit comment
                editComment(v);
            } else {
                if (parentCommentID == null) {//create comment for post
                    createComment(v);
                } else {//create comment for comment
                    createNestedComment(v);
                }
            }

        });
        return root;
    }

    private void createNewPost(View v) {
        editViewModel.getCurrentUser(user ->
                editViewModel.insertPost(new Message(title.getText().toString(), content.getText().toString(), null), user.getUsername(), post -> {
                    if (imageBitmap != null) {
                        MyModel.instance.uploadImage(imageBitmap, post.getPostID(), url -> {
                            post.getPostMessage().setPhoto(url);
                            editViewModel.editPost(post, () -> Navigation.findNavController(v).navigateUp());
                        });
                    }

                }));
    }

    private void editPost(View v) {
        editViewModel.getPost(postID, post -> {

            post.getPostMessage().setContent(content.getText().toString());
            post.getPostMessage().setTitle(title.getText().toString());
            if (imageBitmap != null) {
                MyModel.instance.uploadImage(imageBitmap, post.getPostID(), url -> {
                    post.getPostMessage().setPhoto(url);
                    editViewModel.editPost(post, () -> Navigation.findNavController(v).navigateUp());
                });
            }
        });
    }

    private void editComment(View v) {

        editViewModel.getComment(commentID, comment -> {
            comment.getCommentMessage().setContent(content.getText().toString());
            comment.getCommentMessage().setTitle(title.getText().toString());
            if (imageBitmap != null) {
                MyModel.instance.uploadImage(imageBitmap, comment.getCommentID(), url -> {
                    comment.getCommentMessage().setPhoto(url);
                    editViewModel.editComment(comment, () -> Navigation.findNavController(v).navigateUp());
                });
            }
        });
    }

    private void createComment(View v) {
        editViewModel.getCurrentUser(user ->
                editViewModel.getPost(postID, post ->
                        editViewModel.insertComment(
                                new Message(title.getText().toString(), content.getText().toString(), null
                                ), user.getUsername(), post.getPostID(), null, comment -> {
                                    if (imageBitmap != null) {
                                        MyModel.instance.uploadImage(imageBitmap, comment.getCommentID(), url -> {
                                            comment.getCommentMessage().setPhoto(url);
                                            editViewModel.editComment(comment, () -> Navigation.findNavController(v).navigateUp());
                                        });
                                    }
                                })));
    }

    private void createNestedComment(View v) {
        editViewModel.getCurrentUser(user ->
                editViewModel.getComment(parentCommentID, comment ->
                        editViewModel.insertComment(
                                new Message(title.getText().toString(), content.getText().toString(), null
                                ), user.getUsername(), comment.getPostID(), comment.getCommentID(), comment1 -> {
                                    if (imageBitmap != null) {
                                        MyModel.instance.uploadImage(imageBitmap, comment.getCommentID(), url -> {
                                            comment.getCommentMessage().setPhoto(url);
                                            editViewModel.editComment(comment, () -> Navigation.findNavController(v).navigateUp());
                                        });

                                    }
                                })));
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

    private void initUI(EditText title, EditText content, ProgressBar progressBar) {
        String txt;
        if (isCreate) {
            txt = "Create";
            title.getText().clear();
            content.getText().clear();
        } else {
            txt = "Edit";
            progressBar.setVisibility(View.VISIBLE);
            if (commentID == null) {//edit post
                editViewModel.getPost(postID, post -> {
                    title.setText(post.getPostMessage().getTitle());
                    content.setText(post.getContent());
                    progressBar.setVisibility(View.GONE);
                });
            } else {//edit comment
                editViewModel.getComment(commentID, comment -> {
                    title.setText(comment.getCommentMessage().getTitle());
                    content.setText(comment.getContent());
                    progressBar.setVisibility(View.GONE);
                });

            }
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(txt);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull
            MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

}