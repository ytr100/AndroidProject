package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Comment implements Holdable {
    @Embedded
    private final Message commentMessage;
    //foreign keys
    @NonNull
    private String postID;
    //private Long lastUpdated;
    private boolean isDeleted;
    @PrimaryKey
    @NonNull
    private String commentID;
    @NonNull
    private String commentUsername;
    private String parentCommentID;
    public Comment(@NotNull String title, String content, String photo) {
        this.postID = title; //TODO: get from db
        this.commentID = title; //TODO: get from db
        this.commentUsername = title; //TODO: model method should create comments
        this.parentCommentID = title; //TODO: model method should create comments
        this.commentMessage = new Message(title, content, photo);
        this.isDeleted = false;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getParentCommentID() {
        return parentCommentID;
    }

    public void setParentCommentID(String parentCommentID) {
        this.parentCommentID = parentCommentID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentID.equals(comment.commentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentID);
    }

    public void setPostID(@NonNull String postID) {
        this.postID = postID;
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
