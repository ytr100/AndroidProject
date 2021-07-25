package com.example.androidproject.Model.Entity;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

public class User {
    @PrimaryKey
    @NonNull
    private String username;
    private String email;
    private String photo;
    //private Long lastUpdated;
    private final boolean isDeleted;

    public User(@NotNull String username, String email) {
        this.username = username;
        this.email = email;
        this.photo = null;
        this.isDeleted = false;
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
