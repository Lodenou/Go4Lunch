package com.lodenou.go4lunch.model;

import java.util.List;

public class User {

    private String id;
    private String name;
    private String avatarUrl;
    private Boolean eatSomewhere; // use to sort workmates
    private List<String> favoriteRestaurants;

    public User(String id, String name, String avatarUrl, String restaurantId, String restaurantName, Boolean eatSomewhere, List<String> favoriteRestaurants) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.eatSomewhere = eatSomewhere;
        this.favoriteRestaurants = favoriteRestaurants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getEatSomewhere() {
        return eatSomewhere;
    }

    public void setEatSomewhere(Boolean eatSomewhere) {
        this.eatSomewhere = eatSomewhere;
    }

    public List<String> getFavoriteRestaurants() {
        return favoriteRestaurants;
    }

    public void setFavoriteRestaurants(List<String> favoriteRestaurants) {
        this.favoriteRestaurants = favoriteRestaurants;
    }
}
