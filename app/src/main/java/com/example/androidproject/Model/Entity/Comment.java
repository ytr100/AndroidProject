package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

public class Comment implements Holdable {
    @Embedded
    private final Message commentMessage;
    //private Long lastUpdated;
    private final boolean isDeleted;
    //foreign keys
    @NonNull
    private final String postID;
    @PrimaryKey
    @NonNull
    private String commentID;
    @NonNull
    private String commentUsername;

    public Comment(@NotNull String title, String content, String photo) {
        this.postID = title; //TODO: get from db
        this.commentID = title; //TODO: get from db
        this.commentUsername = title; //TODO: model method should create comments
        this.commentMessage = new Message(title, content, photo);
        this.isDeleted = false;
    }

    @NonNull
    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(@NonNull String commentID) {
        this.commentID = commentID;
    }

    public Message getCommentMessage() {
        return commentMessage;
    }

    @NonNull
    public String getPostID() {
        return postID;
    }

    @NonNull
    public String getCommentUsername() {
        return commentUsername;
    }

    public void setCommentUsername(@NonNull String commentUsername) {
        this.commentUsername = commentUsername;
    }

    @Override
    public String getUsername() {
        return getCommentUsername();
    }

    @Override
    public String getTitle() {
        return getCommentMessage().getTitle();
    }

    @Override
    public String getContent() {
        return getCommentMessage().getContent();
    }

    @Override
    public String getPhoto() {
        return getCommentMessage().getPhoto();
    }
}
