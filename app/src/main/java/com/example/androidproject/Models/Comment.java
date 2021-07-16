package com.example.androidproject.Models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidproject.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Comment {

    final public static String ID = "commentID";
    final public static String TITLE = "commentTitle";
    final public static String CONTENT = "commentContent";
    final public static String LIKES = "commentLikes";
    final public static String POST_ID = "postID";
    final public static String LAST_UPDATED = "lastUpdated";

    final public static String LOCAL_LAST_UPDATED = "commentLastUpdated";
    @PrimaryKey
    @NonNull
    private String commentID;
    private String commentTitle;
    private String commentContent;
    private int commentLikes;  //maybe initialize to 0
    private Long lastUpdated;
    //foreign key
    @NonNull
    private String postID;

    public static void setLocal_lastUpdated(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(LOCAL_LAST_UPDATED, ts);
        editor.commit();
    }

    public static Long getLocal_lastUpdated() {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static Comment fromJson(Map<String, Object> json) {
        Comment comment = new Comment();
        comment.setCommentID((String) json.get(ID));
        comment.setCommentTitle((String) json.get(TITLE));
        comment.setCommentContent((String) json.get(CONTENT));
        comment.setCommentLikes(Integer.parseInt((String) json.get(LIKES)));
        comment.setPostID((String) json.get(POST_ID));
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        if (ts != null)
            comment.setLastUpdated(ts.getSeconds());
        else
            comment.setLastUpdated(0L);
        return comment;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.commentID);
        json.put(TITLE, this.commentTitle);
        json.put(CONTENT, this.commentContent);
        json.put(LIKES, this.commentLikes);
        json.put(POST_ID, this.postID);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
