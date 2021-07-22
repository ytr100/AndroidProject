package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Models.Post;

import java.util.ArrayList;
import java.util.List;
//TODO: livedata
public class PostsViewModel extends ViewModel {
    private List<Post> data;

    public List<Post> getData(){
        if (data == null){
            data = new ArrayList<>();
            for (int i = 0; i<10;i++){
                Post p = new Post();
                p.setPostLikes(i);
                p.setPostUsername("username: "+i);
                p.setPostContent("content: "+i);
                p.setPostTitle("Title: "+i);
                data.add(p);
            }
        }
        return data;
    }


}
