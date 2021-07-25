package com.example.androidproject.Model.Entity;

public class Message {
    private String title;
    private String content;
    private String photo;

    public Message(String title, String content, String photo) {
        this.title = title;
        this.content = content;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
