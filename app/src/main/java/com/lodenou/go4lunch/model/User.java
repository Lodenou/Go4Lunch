package com.lodenou.go4lunch.model;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String restaurantName;
    private String restaurantUid;
    private String restaurantAddress;
    private String avatarUrl;
    private List<String> favoritesRestaurants;
    private boolean favorite;
    private boolean haveRestaurant;

    // DEFAULT CONSTRUCTOR
    public User(){
    }

    public User(String uid, String name, String restaurantName, String restaurantPlaceId, String restaurantAddress, String avatarUrl, List<String> favoritesRestaurants) {
        this.uid = uid;
        this.name = name;
        this.restaurantName = restaurantName;
        this.restaurantUid = restaurantPlaceId;
        this.restaurantAddress = restaurantAddress;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantPlaceId() {
        return restaurantUid;
    }

    public void setRestaurantPlaceId(String restaurantUid) {
        this.restaurantUid = restaurantUid;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public Boolean haveFavoriteRestaurant() {
        return favorite;
    }

    public void setFavorite(boolean tfFavorite) {
        this.favorite = tfFavorite;
    }


    public Boolean HaveRestaurantSet(){
        return haveRestaurant;
    }

    public void setRestaurant(Boolean tf) {
        this.haveRestaurant = tf;
    }

}

