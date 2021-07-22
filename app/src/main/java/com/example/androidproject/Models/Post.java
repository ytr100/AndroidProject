package com.example.androidproject.Models;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.androidproject.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "username",
        childColumns = "postUsername",
        onDelete = ForeignKey.CASCADE)})
public class Post {

    final public static String ID = "postID";
    final public static String TITLE = "postTitle";
    final public static String CONTENT = "postContent";
    final public static String LIKES = "postLikes";
    final public static String USERNAME = "postUsername";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";

    final public static String LOCAL_LAST_UPDATED = "postLastUpdated";
    @PrimaryKey
    @NonNull
    private String postID;
    private String postTitle;
    private String postContent;
    private int postLikes; //maybe initialize to 0
    private Long lastUpdated;
    private boolean isDeleted;

    //foreign key
    @NonNull
    private String postUsername;

    public static void setLocal_lastUpdated(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(LOCAL_LAST_UPDATED, ts);
        editor.apply();
    }

    public static Long getLocal_lastUpdated() {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static Post fromJson(Map<String, Object> json) {
        Post post = new Post();
        post.setPostID((String) json.get(ID));
        post.setPostTitle((String) json.get(TITLE));
        post.setPostContent((String) json.get(CONTENT));
        post.setPostLikes(Integer.parseInt((String) json.get(LIKES)));
        post.setPostUsername((String) json.get(USERNAME));
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        if (ts != null)
            post.setLastUpdated(ts.getSeconds());
        else
            post.setLastUpdated(0L);
        post.setDeleted((boolean) json.get(IS_DELETED));
        return post;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.postID);
        json.put(TITLE, this.postTitle);
        json.put(CONTENT, this.postContent);
        json.put(LIKES, this.postLikes);
        json.put(USERNAME, this.postUsername);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, this.isDeleted);
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
