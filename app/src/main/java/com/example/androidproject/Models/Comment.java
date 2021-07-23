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

@Entity(foreignKeys = {@ForeignKey(entity = Post.class,
        parentColumns = "postID",
        childColumns = "postID",
        onDelete = ForeignKey.CASCADE)})
public class Comment {

    final public static String ID = "commentID";
    final public static String TITLE = "commentTitle";
    final public static String CONTENT = "commentContent";
    final public static String LIKES = "commentLikes";
    final public static String POST_ID = "postID";
    final public static String PARENT_COMMENT_ID = "parentCommentID";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";

    final public static String LOCAL_LAST_UPDATED = "commentLastUpdated";

    private static Long IDCounter = 1L;

    @PrimaryKey
    @NonNull
    private String commentID;
    private String commentTitle;
    private String commentContent;
    private int commentLikes;  //maybe initialize to 0
    private Long lastUpdated;
    private boolean isDeleted;

    //foreign key
    @NonNull
    private String postID;

    private String parentCommentID;


    public static void setLocal_lastUpdated(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(LOCAL_LAST_UPDATED, ts);
        editor.apply();
    }

    public static Long getLocal_lastUpdated() {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static Comment createComment(String title, String content, String postID, String parentCommentID){
        Map<String, Object> json = new HashMap<>();

        json.put(ID, IDCounter.toString());
        IDCounter++;
        json.put(TITLE, title);
        json.put(CONTENT, content);
        json.put(LIKES, 0);
        json.put(POST_ID, postID);
        json.put(PARENT_COMMENT_ID, parentCommentID);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, false);

        return fromJson(json);
    }

    public static Comment fromJson(Map<String, Object> json) {
        Comment comment = new Comment();
        comment.setCommentID((String) json.get(ID));
        comment.setCommentTitle((String) json.get(TITLE));
        comment.setCommentContent((String) json.get(CONTENT));
        comment.setCommentLikes(Integer.parseInt((String) json.get(LIKES)));
        comment.setPostID((String) json.get(POST_ID));
        comment.setParentCommentID((String) json.get(PARENT_COMMENT_ID));
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        if (ts != null)
            comment.setLastUpdated(ts.getSeconds());
        else
            comment.setLastUpdated(0L);
        comment.setDeleted((boolean) json.get(IS_DELETED));
        return comment;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, this.commentID);
        json.put(TITLE, this.commentTitle);
        json.put(CONTENT, this.commentContent);
        json.put(LIKES, this.commentLikes);
        json.put(POST_ID, this.postID);
        json.put(PARENT_COMMENT_ID, this.parentCommentID);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, this.isDeleted);
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

    public String getParentCommentID() {
        return parentCommentID;
    }

    public void setParentCommentID(String parentCommentID) {
        this.parentCommentID = parentCommentID;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
