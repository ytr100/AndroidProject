package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Post;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO: livedata
public class CommentsViewModel extends ViewModel {
    private List<Comment> data;
    public CommentsViewModel(){
        data = new ArrayList<>();
    }

    public List<Comment> getData(Post p){
        data = MyTestModel.instance.db.get(p);
        Collections.sort(data,(o1, o2) -> o1.getCommentID().compareTo(o2.getCommentID()));
        return data;
    }
    public List<Comment> getCurrent(){
        return data;
    }


}