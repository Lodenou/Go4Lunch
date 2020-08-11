package com.lodenou.go4lunch.model;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String avatarUrl;
    private String restaurantId;
    private String restaurantName;
    private List<String> favoritesRestaurants;

    public User(String uid, String name, String avatarUrl, String restaurantId, String restaurantName, List<String> favoritesRestaurants) {
        this.uid = uid;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.favoritesRestaurants = favoritesRestaurants;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<String> getFavoritesRestaurants() {
        return favoritesRestaurants;
    }

    public void setFavoritesRestaurants(List<String> favoritesRestaurants) {
        this.favoritesRestaurants = favoritesRestaurants;
    }
}

