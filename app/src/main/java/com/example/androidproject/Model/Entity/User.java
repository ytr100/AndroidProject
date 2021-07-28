package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String PHOTO = "photo";
    final public static String EMAIL = "email";
    final public static String USERNAME = "username";
    final public static String IS_DELETED = "isDeleted";

    @PrimaryKey
    @NonNull
    private String username;
    private String email;
    private String photo;
    private Long lastUpdated;
    private boolean isDeleted;

    public User(@NotNull String username, String email) {
        this.username = username;
        this.email = email;
        this.photo = "";
        this.isDeleted = false;
    }

    public User() {

    }
    public static User fromJson(Map<String, Object> json) {
        User user = new User();
        user.setEmail((String) json.get(EMAIL));
        user.setUsername((String)json.get(USERNAME));
        user.setPhoto((String) json.get(PHOTO));//TODO: image
//        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
//        if (ts != null)
//            post.setLastUpdated(ts.getSeconds());
//        else
//            post.setLastUpdated(0L);
        user.setDeleted((boolean) json.get(IS_DELETED));
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, username);
        //json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, isDeleted);
        json.put(PHOTO,photo);//TODO: image
        json.put(EMAIL,email);
        return json;
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

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
