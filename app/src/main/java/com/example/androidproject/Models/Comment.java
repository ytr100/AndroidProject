package com.example.androidproject.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Comment {

    @PrimaryKey
    @NonNull
    private String comment_ID;
    private String comment_Title;
    private String comment_Content;
    private int comment_Likes;  //maybe initialize to 0

    //foreign key
    @NonNull
    private String post_ID;

    final private static String ID = "comment_id";
    final private static String TITLE = "comment_title";
    final private static String CONTENT = "comment_content";
    final private static String LIKES = "comment_likes";
    final private static String POST_ID = "post_id";

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.comment_ID);
        json.put(TITLE, this.comment_Title);
        json.put(CONTENT, this.comment_Content);
        json.put(LIKES, this.comment_Likes);
        json.put(POST_ID, this.post_ID);
        return json;
    }
    static public Comment createUser(Map<String,Object> json) {
        Comment comment = new Comment();
        comment.setComment_ID((String)json.get(ID));
        comment.setComment_Title((String)json.get(TITLE));
        comment.setComment_Content((String)json.get(CONTENT));
        comment.setComment_Likes(Integer.parseInt((String)json.get(LIKES)));
        comment.setPost_ID((String)json.get(POST_ID));
        return comment;
    }


    @NonNull
    public String getComment_ID() {
        return comment_ID;
    }

    public void setComment_ID(@NonNull String comment_ID) {
        this.comment_ID = comment_ID;
    }

    public String getComment_Title() {
        return comment_Title;
    }

    public void setComment_Title(String comment_Title) {
        this.comment_Title = comment_Title;
    }

    public String getComment_Content() {
        return comment_Content;
    }

    public void setComment_Content(String comment_Content) {
        this.comment_Content = comment_Content;
    }

    public int getComment_Likes() {
        return comment_Likes;
    }

    public void setComment_Likes(int comment_Likes) {
        this.comment_Likes = comment_Likes;
    }

    @NonNull
    public String getPost_ID() {
        return post_ID;
    }

    public void setPost_ID(@NonNull String post_ID) {
        this.post_ID = post_ID;
    }
}
