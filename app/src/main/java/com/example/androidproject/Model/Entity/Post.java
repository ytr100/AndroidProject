package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Post implements Holdable {

    //private Long lastUpdated;
    private final boolean isDeleted;
    @PrimaryKey
    @NonNull
    private String postID;
    @Embedded
    private final Message postMessage;
    //foreign key
    @NonNull
    private String postUsername;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postID, post.postID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID);
    }

    public Post(@NotNull String title, String content, String photo) {
        this.postID = title; //TODO: implement increment
        this.postMessage = new Message(title, content, photo);
        this.isDeleted = false;
        this.postUsername = title;//TODO: model method should create posts
    }

    public Message getPostMessage() {
        return postMessage;
    }

    @NonNull
    public String getPostID() {
        return postID;
    }

    public void setPostID(@NonNull String postID) {
        this.postID = postID;
    }

    @NonNull
    public String getPostUsername() {
        return postUsername;
    }

    public void setPostUsername(@NonNull String postUsername) {
        this.postUsername = postUsername;
    }

    @Override
    public String getUsername() {
        return getPostUsername();
    }

    @Override
    public String getTitle() {
        return getPostMessage().getTitle();
    }

    @Override
    public String getContent() {
        return getPostMessage().getContent();
    }

    @Override
    public String getPhoto() {
        return getPostMessage().getPhoto();
    }
}
