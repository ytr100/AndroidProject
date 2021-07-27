package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentsViewModel extends ViewModel {
    public LiveData<List<Comment>> data;

    public CommentsViewModel() {
        data = new MutableLiveData<>(new ArrayList<>());
    }
    public LiveData<List<Comment>> getCommentsOfPost(String postID){
        data = MyModel.instance.getCommentsOfPost(postID);
        List<Comment> test = data.getValue();
        return data;
    }
    public LiveData<List<Comment>> getCommentsOfComment(String parentCommentID) {
        data = MyModel.instance.getCommentsOfComment(parentCommentID);
        return data;
    }
    public LiveData<List<Comment>> getCommentsOfUser(String username){
        data = MyModel.instance.getCommentsOfUser(username);
        return data;
    }


    public void deleteComment(Comment c) {//TODO: implement
    }
}