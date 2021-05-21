package com.lodenou.go4lunch.model;

import java.util.Date;

public class Message {


    private String message;
    private User userSender;
    private Date dateCreated;
    private String urlImage;


    public Message(String message, User user) {
        this.message = message;
        this.userSender = user;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User user) {
        this.userSender = user;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCerated) {
        this.dateCreated = dateCerated;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
