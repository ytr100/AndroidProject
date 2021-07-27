package com.example.androidproject.UI.MainApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.R;

public class EditFragment extends Fragment {

    private EditViewModel editViewModel;
    private String postID;
    private String commentID;
    private String parentCommentID;
    private boolean isCreate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        View root = inflater.inflate(R.layout.edit_fragment, container, false);
        setHasOptionsMenu(true);

        EditText title = root.findViewById(R.id.edit_title);
        EditText content = root.findViewById(R.id.edit_content);
        Button save = root.findViewById(R.id.edit_save);

        EditFragmentArgs args = EditFragmentArgs.fromBundle(getArguments());
        postID = args.getPostID();
        commentID = args.getCommentID();
        parentCommentID = args.getParentCommentID();
        isCreate = args.getIsCreate();

        initUI(title,content);
        //TODO: only posts for now, need to check comments
        save.setOnClickListener(v -> {
           if(postID == null){//create a new post
                editViewModel.insertPost(new Post(title.getText().toString(),content.getText().toString(),""),editViewModel.getCurrentUser().getUsername());
            }else{
               Post p = editViewModel.getPost(postID);
               p.getPostMessage().setContent(content.getText().toString());
               p.getPostMessage().setTitle(title.getText().toString());
               editViewModel.editPost(p);
               //TODO: insert edit of comments, delete and reorder the ui code then model and images
           }
           Navigation.findNavController(v).navigateUp();
        });
        return root;
    }
    private void initUI(EditText title, EditText content){
        String txt;
        if(isCreate){
            txt = "Create";
            title.getText().clear();
            content.getText().clear();
        }
        else{
            txt = "Edit";
            //TODO: fetch current data from db
            Post p = editViewModel.getPost(postID);
            title.setText(p.getPostMessage().getTitle());
            content.setText(p.getContent());
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