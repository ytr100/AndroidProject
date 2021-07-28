package com.example.androidproject.Model.Entity;

import java.util.Objects;

public class Message {
    private String title;
    private String content;
    private String photo;

    public Message(String title, String content, String photo) {
        this.title = title;
        this.content = content;
        this.photo = photo;
    }

    public Message() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(title, message.title) &&
                Objects.equals(content, message.content) &&
                Objects.equals(photo, message.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, photo);
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
