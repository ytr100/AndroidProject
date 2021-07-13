package com.example.androidproject.Models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity
public class User {

    final private static String USERNAME = "username";
    final private static String PASSWORD = "password";
    final private static String EMAIL = "email";
    final private static String PHOTO = "photo";
    @PrimaryKey
    @NonNull
    private String username;
    private String password;
    private String email;
    private String photo;

    static public User fromJson(Map<String, Object> json) {
        User user = new User();
        user.setUsername((String) json.get(USERNAME));
        user.setPassword((String) json.get(PASSWORD));
        user.setEmail((String) json.get(EMAIL));
        user.setPhoto((String) json.get(PHOTO));
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, this.username);
        json.put(PASSWORD, this.password);
        json.put(EMAIL, this.email);
        json.put(PHOTO, this.photo);
        return json;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
