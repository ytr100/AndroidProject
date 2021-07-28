package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Listeners.OnCommentsCompleteListener;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;

import java.util.ArrayList;
import java.util.List;

public class CommentsViewModel extends ViewModel {
    public LiveData<List<Comment>> data;
    private List<Comment> queryResult;

    public CommentsViewModel() {
        data = MyModel.instance.allComments;
        queryResult = new ArrayList<>();
    }

    public void getCommentsOfPost(String postID, OnCommentsCompleteListener actionComplete) {

        MyModel.instance.getCommentsOfPost(postID, comments -> {
            queryResult = comments;
            actionComplete.onComplete(queryResult);
        });
    }

    public void getCommentsOfComment(String parentCommentID, OnCommentsCompleteListener actionComplete) {
        MyModel.instance.getCommentsOfComment(parentCommentID, comments -> {
            queryResult = comments;
            actionComplete.onComplete(queryResult);
        });
    }

    public void getCommentsOfUser(String username, OnCommentsCompleteListener actionComplete) {
        MyModel.instance.getCommentsOfComment(username, comments -> {
            queryResult = comments;
            actionComplete.onComplete(queryResult);
        });
    }

    public void deleteComment(Comment c, OnDBActionComplete actionComplete) {
        MyModel.instance.deleteComment(c, actionComplete);
    }

    public LiveData<List<Comment>> getAllComments() {
        return data;
    }

    public List<Comment> getCurrent() {
        if (queryResult != null)
            return queryResult;
        return new ArrayList<>();
    }
}