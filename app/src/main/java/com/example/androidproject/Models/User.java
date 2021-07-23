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
public class User {

    final public static String USERNAME = "username";
    final public static String EMAIL = "email";
    final public static String PHOTO = "photo";
    final public static String LAST_UPDATED = "lastUpdated";
    final public static String IS_DELETED = "isDeleted";

    final public static String LOCAL_LAST_UPDATED = "userLastUpdated";

    @PrimaryKey
    @NonNull
    private String username;
    private String email;
    private String photo;
    private Long lastUpdated;
    private boolean isDeleted;

    public static void setLocal_lastUpdated(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(LOCAL_LAST_UPDATED, ts);
        editor.apply();
    }

    public static Long getLocal_lastUpdated() {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static User createUser(String username, String email) {
        Map<String, Object> json = new HashMap<>();

        json.put(USERNAME, username);
        json.put(EMAIL,email);
        json.put(PHOTO, null);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, false);

        return fromJson(json);
    }

    public static User fromJson(Map<String, Object> json) {
        User user = new User();
        user.setUsername((String) json.get(USERNAME));
        user.setEmail((String) json.get(EMAIL));
        user.setPhoto((String) json.get(PHOTO));
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        if (ts != null)
            user.setLastUpdated(ts.getSeconds());
        else
            user.setLastUpdated(0L);
        user.setDeleted((boolean) json.get(IS_DELETED));
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, this.username);
        json.put(EMAIL, this.email);
        json.put(PHOTO, this.photo);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(IS_DELETED, this.isDeleted);
        return json;
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
