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

    final private static String USERNAME = "username";
    final private static String EMAIL = "email";
    final private static String PHOTO = "photo";
    final private static String LAST_UPDATED = "lastUpdated";

    final private static String LOCAL_LAST_UPDATED = "userLastUpdated";

    @PrimaryKey
    @NonNull
    private String username;
    private String email;
    private String photo;
    private Long lastUpdated;


    public static void setLocal_lastUpdated(Long ts) {
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(LOCAL_LAST_UPDATED, ts);
        editor.commit();
    }

    public static Long getLocal_lastUpdated(Long ts) {
        return MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong(LOCAL_LAST_UPDATED, 0);
    }

    public User fromJson(Map<String, Object> json) {
        User user = new User();
        user.setUsername((String) json.get(USERNAME));
        user.setEmail((String) json.get(EMAIL));
        user.setPhoto((String) json.get(PHOTO));
        Timestamp ts = (Timestamp) json.get(LAST_UPDATED);
        if (ts != null)
            user.setLastUpdated(ts.getSeconds());
        else
            user.setLastUpdated(0L);
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, this.username);
        json.put(EMAIL, this.email);
        json.put(PHOTO, this.photo);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
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

}
