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
public class Post implements Holdable {

    final public static String ID = "postID";
    final public static String MESSAGE = "postMessage";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";
    final public static String USERNAME = "postUsername";
    private static final String POST_LAST_UPDATE_DATE = "postLastUpdate";
    private Long lastUpdated;
    private boolean isDeleted;
    @PrimaryKey
    @NonNull
    private String postID;
    @Embedded
    private Message postMessage;
    private String postUsername;

    public Post() {
    }

    public Post(Message m, String postUsername) {
        this.postMessage = m;
        this.isDeleted = false;
        this.postUsername = postUsername;
    }

    public static Post fromJson(Map<String, Object> json) {
        Post post = new Post();
        post.setPostID((String) (json.get(ID)));
        HashMap<String, String> message = (HashMap<String, String>) (json.get(MESSAGE));
        String title = message.get(Message.TITLE);
        String content = message.get(Message.CONTENT);
        String photo = message.get(Message.PHOTO);
        post.setPostMessage(new Message(title, content, photo));
        post.setPostUsername((String) (json.get(USERNAME)));
        Timestamp ts = (Timestamp) (json.get(LAST_UPDATED));
        if (ts != null)
            post.setLastUpdated(ts.getSeconds());
        else
            post.setLastUpdated(0L);
        post.setDeleted((boolean) (json.get(IS_DELETED)));
        return post;
    }

    static public Long getLocalLastUpdateTime() {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(POST_LAST_UPDATE_DATE, 0);
    }

    static public void setLocalLastUpdateTime(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(POST_LAST_UPDATE_DATE, ts);
        editor.commit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return isDeleted == post.isDeleted &&
                Objects.equals(lastUpdated, post.lastUpdated) &&
                postID.equals(post.postID) &&
                Objects.equals(postMessage, post.postMessage) &&
                Objects.equals(postUsername, post.postUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID);
    }

    public Map<String, Object> toJsonWithoutID() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, postUsername);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, isDeleted);
        json.put(MESSAGE, postMessage);
        return json;
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
