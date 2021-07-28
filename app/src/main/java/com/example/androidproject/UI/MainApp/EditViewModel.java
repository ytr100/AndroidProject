package com.example.androidproject.UI.MainApp;

import androidx.lifecycle.ViewModel;

import com.example.androidproject.Model.Database.MyModel;
import com.example.androidproject.Model.Entity.Comment;
import com.example.androidproject.Model.Entity.Message;
import com.example.androidproject.Model.Entity.Post;
import com.example.androidproject.Model.Listeners.GetCommentListener;
import com.example.androidproject.Model.Listeners.GetPostListener;
import com.example.androidproject.Model.Listeners.GetUserListener;
import com.example.androidproject.Model.Listeners.OnDBActionComplete;

public class EditViewModel extends ViewModel {

    public void insertPost(Message m, String username, GetPostListener actionComplete){
        MyModel.instance.insertPost(m,username,actionComplete);
    }
    public void editPost(Post p, OnDBActionComplete actionComplete){
        MyModel.instance.editPost(p,actionComplete);
    }
    public void insertComment(Message m, String username, String postID, String parentCommentID, GetCommentListener actionComplete){
        MyModel.instance.insertComment(m,username,postID,parentCommentID,actionComplete);
    }
    public void editComment(Comment c, OnDBActionComplete actionComplete){
        MyModel.instance.editComment(c, actionComplete);
    }
    public void getCurrentUser(GetUserListener listener){
        MyModel.instance.getUserByID(MyModel.CURRENT_USER, listener);
    }
    public void getPost(String postID, GetPostListener listener){
         MyModel.instance.getPostByID(postID,listener);
    }
    public void getComment(String commentID, GetCommentListener listener){
        MyModel.instance.getCommentByID(commentID,listener);
    }
}