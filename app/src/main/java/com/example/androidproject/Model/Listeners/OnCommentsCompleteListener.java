package com.example.androidproject.Model.Listeners;

import com.example.androidproject.Model.Entity.Comment;

import java.util.List;

public interface OnCommentsCompleteListener {
    void onComplete(List<Comment> comments);
}
