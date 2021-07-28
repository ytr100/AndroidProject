package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Post implements Holdable {

    final public static String ID = "postID";
    final public static String MESSAGE = "postMessage";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";
    final public static String USERNAME = "postUsername";
    private Long lastUpdated;
    private boolean isDeleted;
    @PrimaryKey
    @NonNull
    private String postID;
    @Embedded
    private Message postMessage;
    private String postUsername;

        public static Post fromJson(Map<String, Object> json) {
        Post post = new Post();
        post.setPostID((String) json.get(ID));
        post.setPostMessage((Message)json.get(MESSAGE));
        post.setPostUsername((String) json.get(USERNAME));
//        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
//        if (ts != null)
//            post.setLastUpdated(ts.getSeconds());
//        else
//            post.setLastUpdated(0L);
        post.setDeleted((boolean) json.get(IS_DELETED));
        return post;
    }

    public Map<String, Object> toJsonWithoutID() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, postUsername);
        //json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, isDeleted);
        json.put(MESSAGE,postMessage);
        return json;
    }


    public Post() {
    }

    public Post(Message m, String postUsername) {
        this.postMessage = m;
        this.isDeleted = false;
        this.postUsername = postUsername;
    }

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

    public Message getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(Message postMessage) {
        this.postMessage = postMessage;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
