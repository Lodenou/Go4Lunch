package com.lodenou.go4lunch.model;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String avatarUrl;
    private List<String> favoritesRestaurants;
    private boolean favorite;

    public User(){

    }

    public User(String uid, String name, String avatarUrl, List<String> favoritesRestaurants) {
        this.uid = uid;
        this.name = name;
        this.avatarUrl = avatarUrl;

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

    public List<String> getFavoritesRestaurants() {
        return favoritesRestaurants;
    }

    public void setFavoritesRestaurants(List<String> favoritesRestaurants) {
        this.favoritesRestaurants = favoritesRestaurants;
    }

    public Boolean haveFavoriteRestaurant() {
        return favorite;
    }
    public void setFavorite(boolean tfFavorite) {
        this.favorite = tfFavorite;
    }
}

