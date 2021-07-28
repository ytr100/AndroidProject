package com.example.androidproject.Model.Entity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidproject.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Comment implements Holdable {
    final public static String COMMENT_ID = "commentID";
    final public static String MESSAGE = "commentMessage";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";
    final public static String USERNAME = "commentUsername";
    final public static String PARENT_COMMENT_ID = "parentCommentID";
    final public static String POST_ID = "postID";
    private static final String COMMENT_LAST_UPDATE_DATE = "commentLastUpdate";
    @Embedded
    private Message commentMessage;
    @NonNull
    private String postID;
    private Long lastUpdated;
    private boolean isDeleted;
    @PrimaryKey
    @NonNull
    private String commentID;
    @NonNull
    private String commentUsername;
    private String parentCommentID;

    public Comment(Message m, String username, String postID, String parentCommentID) {
        this.commentUsername = username;
        this.parentCommentID = parentCommentID;
        this.commentMessage = m;
        this.isDeleted = false;
        this.postID = postID;
    }

    public Comment() {
    }

    public static Comment fromJson(Map<String, Object> json) {
        Comment c = new Comment();
        HashMap<String, String> message = (HashMap<String, String>) (json.get(MESSAGE));
        String title = message.get(Message.TITLE);
        String content = message.get(Message.CONTENT);
        String photo = message.get(Message.PHOTO);
        c.setCommentMessage(new Message(title, content, photo));
        c.setCommentID((String) json.get(COMMENT_ID));
        c.setDeleted((boolean) json.get(IS_DELETED));
        c.setPostID((String) json.get(POST_ID));
        c.setParentCommentID((String) json.get(PARENT_COMMENT_ID));
        c.setCommentUsername((String) json.get(USERNAME));

        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        if (ts != null)
            c.setLastUpdated(ts.getSeconds());
        else
            c.setLastUpdated(0L);
        return c;
    }

    static public Long getLocalLastUpdateTime() {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(COMMENT_LAST_UPDATE_DATE, 0);
    }

    static public void setLocalLastUpdateTime(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(COMMENT_LAST_UPDATE_DATE, ts);
        editor.commit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return isDeleted == comment.isDeleted &&
                Objects.equals(commentMessage, comment.commentMessage) &&
                postID.equals(comment.postID) &&
                Objects.equals(lastUpdated, comment.lastUpdated) &&
                commentID.equals(comment.commentID) &&
                commentUsername.equals(comment.commentUsername) &&
                Objects.equals(parentCommentID, comment.parentCommentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentID);
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, Object> toJsonWithoutID() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, commentUsername);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, isDeleted);
        json.put(MESSAGE, commentMessage);
        json.put(PARENT_COMMENT_ID, parentCommentID);
        json.put(POST_ID, postID);
        return json;
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

    public void setCommentMessage(Message commentMessage) {
        this.commentMessage = commentMessage;
    }

    @NonNull
    public String getPostID() {
        return postID;
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
