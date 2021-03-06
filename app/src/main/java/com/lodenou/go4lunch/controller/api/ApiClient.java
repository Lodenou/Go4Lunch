package com.lodenou.go4lunch.controller.api;

import com.lodenou.go4lunch.BuildConfig;
import com.lodenou.go4lunch.model.autocomplete.Autocomplete;
import com.lodenou.go4lunch.model.detail.GoogleDetailResult;
import com.lodenou.go4lunch.model.nearbysearch.GoogleSearchResults;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Client for API calls
 */
public interface ApiClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @GET("nearbysearch/json?type=restaurant&key=" + BuildConfig.GOOGLE_MAP_API_KEY)
    Call<GoogleSearchResults> getNearbyPlaces(@Query("location") String location, @Query("radius") int radius);

    @GET("details/json?fields=name,vicinity,international_phone_number,website,photo,rating,geometry,place_id,opening_hours&key=" + BuildConfig.GOOGLE_MAP_API_KEY)
    Call<GoogleDetailResult> getPlaceDetails(@Query("place_id") String placeId);

    @GET("autocomplete/json?types=establishment&radius=5000&strictbounds&key=" + BuildConfig.GOOGLE_MAP_API_KEY)
    Call<Autocomplete> getAutocomplete(@Query("input") String input, @Query("location") String location, @Query("radius") int radius);

}