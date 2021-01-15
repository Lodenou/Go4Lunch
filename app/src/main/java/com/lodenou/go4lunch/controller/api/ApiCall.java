package com.lodenou.go4lunch.controller.api;

import androidx.annotation.Nullable;

import com.lodenou.go4lunch.model.nearbysearch.GoogleSearchResults;
import com.lodenou.go4lunch.model.nearbysearch.Restaurant;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall{


    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable List<Result> users);
        void onFailure();
    }

    // 2 - Public method to start fetching users following by Jake Wharton
    public static void fetchRestaurantsNear(Callbacks callbacks, String location, int radius){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        ApiClient apiClient = ApiClient.retrofit.create(ApiClient.class);

        // 2.3 - Create the call on Github API
        Call<GoogleSearchResults> call = apiClient.getNearbyPlaces(location, radius);
        // 2.4 - Start the call
        call.enqueue(new Callback<GoogleSearchResults>() {

            @Override
            public void onResponse(Call<GoogleSearchResults> call, Response<GoogleSearchResults> response) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body().getResults());
            }

            @Override
            public void onFailure(Call<GoogleSearchResults> call, Throwable t) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

    //TODO pour detail
}
