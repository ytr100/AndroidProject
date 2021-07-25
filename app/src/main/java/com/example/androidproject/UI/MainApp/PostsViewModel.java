package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//TODO: livedata
public class PostsViewModel extends ViewModel {
    private List<Post> data;

    public List<Post> getData(){
        if (data == null){
            data = new ArrayList<>();
            data.addAll(MyTestModel.instance.db.keySet());
            Collections.sort(data,(o1, o2) -> o1.getPostID().compareTo(o2.getPostID()));
        }
        return data;
    }


}
