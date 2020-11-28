package com.lodenou.go4lunch.model.nearbysearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lodenou.go4lunch.model.nearbysearch.Northeast;
import com.lodenou.go4lunch.model.nearbysearch.Southwest;

public class Viewport {

    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }
}