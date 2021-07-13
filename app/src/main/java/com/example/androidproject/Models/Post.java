package com.example.androidproject.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {

    final private static String ID = "postID";
    final private static String TITLE = "postTitle";
    final private static String CONTENT = "postContent";
    final private static String LIKES = "postLikes";
    final private static String USERNAME = "postUsername";
    @PrimaryKey
    @NonNull
    private String postID;
    private String postTitle;
    private String postContent;
    private int postLikes; //maybe initialize to 0
    //foreign key
    @NonNull
    private String postUsername;

    static public Post fromJson(Map<String, Object> json) {
        Post post = new Post();
        post.setPostID((String) json.get(ID));
        post.setPostTitle((String) json.get(TITLE));
        post.setPostContent((String) json.get(CONTENT));
        post.setPostLikes(Integer.parseInt((String) json.get(LIKES)));
        post.setPostUsername((String) json.get(USERNAME));
        return post;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.postID);
        json.put(TITLE, this.postTitle);
        json.put(CONTENT, this.postContent);
        json.put(LIKES, this.postLikes);
        json.put(USERNAME, this.postUsername);
        return json;
    }

    @NonNull
    public String getPostID() {
        return postID;
    }

    public void setPostID(@NonNull String postID) {
        this.postID = postID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(int postLikes) {
        this.postLikes = postLikes;
    }

    @NonNull
    public String getPostUsername() {
        return postUsername;
    }

    public void setPostUsername(@NonNull String postUsername) {
        this.postUsername = postUsername;
    }
}
