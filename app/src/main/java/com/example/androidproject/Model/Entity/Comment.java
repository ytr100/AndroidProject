package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Entity
public class Comment implements Holdable {
    public void setCommentMessage(Message commentMessage) {
        this.commentMessage = commentMessage;
    }

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
    final public static String COMMENT_ID = "commentID";
    final public static String MESSAGE = "commentMessage";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";
    final public static String USERNAME = "commentUsername";
    final public static String PARENT_COMMENT_ID = "parentCommentID";
    final public static String POST_ID = "postID";

    public Comment(Message m, String username, String postID,String parentCommentID) {
        this.commentUsername = username;
        this.parentCommentID = parentCommentID;
        this.commentMessage = m;
        this.isDeleted = false;
        this.postID = postID;
    }
    public static Comment fromJson(Map<String, Object> json) {
        Comment c = new Comment();
       c.setCommentMessage((Message) json.get(MESSAGE));
       c.setCommentID((String)json.get(COMMENT_ID));
       c.setDeleted((boolean) json.get(IS_DELETED));
       c.setPostID((String)json.get(POST_ID));
       if(json.get(PARENT_COMMENT_ID) == null)
            c.setParentCommentID(null);
       else c.setParentCommentID((String)json.get(PARENT_COMMENT_ID));
       c.setCommentUsername((String)json.get(USERNAME));

//        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
//        if (ts != null)
//            post.setLastUpdated(ts.getSeconds());
//        else
//            post.setLastUpdated(0L);
        return c;
    }
    public Map<String, Object> toJsonWithoutID() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, commentUsername);
        //json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, isDeleted);
        json.put(MESSAGE,commentMessage);
        json.put(PARENT_COMMENT_ID,parentCommentID);
        json.put(POST_ID,postID);
        return json;
    }

    public Comment() {
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
