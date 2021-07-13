package com.example.androidproject.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Comment {

    final private static String ID = "commentID";
    final private static String TITLE = "commentTitle";
    final private static String CONTENT = "commentContent";
    final private static String LIKES = "commentLikes";
    final private static String POST_ID = "postID";
    @PrimaryKey
    @NonNull
    private String commentID;
    private String commentTitle;
    private String commentContent;
    private int commentLikes;  //maybe initialize to 0
    //foreign key
    @NonNull
    private String postID;

    static public Comment fromJson(Map<String, Object> json) {
        Comment comment = new Comment();
        comment.setCommentID((String) json.get(ID));
        comment.setCommentTitle((String) json.get(TITLE));
        comment.setCommentContent((String) json.get(CONTENT));
        comment.setCommentLikes(Integer.parseInt((String) json.get(LIKES)));
        comment.setPostID((String) json.get(POST_ID));
        return comment;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.commentID);
        json.put(TITLE, this.commentTitle);
        json.put(CONTENT, this.commentContent);
        json.put(LIKES, this.commentLikes);
        json.put(POST_ID, this.postID);
        return json;
    }

    @NonNull
    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(@NonNull String commentID) {
        this.commentID = commentID;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentLikes() {
        return commentLikes;
    }

    public void setCommentLikes(int commentLikes) {
        this.commentLikes = commentLikes;
    }

    @NonNull
    public String getPostID() {
        return postID;
    }

    public void setPostID(@NonNull String postID) {
        this.postID = postID;
    }
}
