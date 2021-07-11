package com.example.androidproject.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {

    @PrimaryKey
    @NonNull
    private String post_ID;
    private String post_Title;
    private String post_Content;
    private int post_Likes; //maybe initialize to 0

    //foreign key
    @NonNull
    private String post_Username;

    final private static String ID = "post_id";
    final private static String TITLE = "post_title";
    final private static String CONTENT = "post_content";
    final private static String LIKES = "post_likes";
    final private static String USERNAME = "post_username";

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.post_ID);
        json.put(TITLE, this.post_Title);
        json.put(CONTENT, this.post_Content);
        json.put(LIKES, this.post_Likes);
        json.put(USERNAME, this.post_Username);
        return json;
    }
    static public Post createUser(Map<String,Object> json) {
        Post post = new Post();
        post.setPost_ID((String)json.get(ID));
        post.setPost_Title((String)json.get(TITLE));
        post.setPost_Content((String)json.get(CONTENT));
        post.setPost_Likes(Integer.parseInt((String)json.get(LIKES)));
        post.setPost_Username((String)json.get(USERNAME));
        return post;
    }


    @NonNull
    public String getPost_ID() {
        return post_ID;
    }

    public void setPost_ID(@NonNull String post_ID) {
        this.post_ID = post_ID;
    }

    public String getPost_Title() {
        return post_Title;
    }

    public void setPost_Title(String post_Title) {
        this.post_Title = post_Title;
    }

    public String getPost_Content() {
        return post_Content;
    }

    public void setPost_Content(String post_Content) {
        this.post_Content = post_Content;
    }

    public int getPost_Likes() {
        return post_Likes;
    }

    public void setPost_Likes(int post_Likes) {
        this.post_Likes = post_Likes;
    }

    @NonNull
    public String getPost_Username() {
        return post_Username;
    }

    public void setPost_Username(@NonNull String post_Username) {
        this.post_Username = post_Username;
    }
}
