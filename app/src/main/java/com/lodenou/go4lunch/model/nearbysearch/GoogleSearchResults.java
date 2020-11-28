package com.lodenou.go4lunch.model.nearbysearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleSearchResults {

    @SerializedName("results")
    private List<Result> mResultSearches;
    @SerializedName("status")
    private String mStatus;



    public List<Result> getResults() {
        return mResultSearches;
    }

    public void setResults(List<Result> resultSearches) {
        mResultSearches = resultSearches;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }


}
