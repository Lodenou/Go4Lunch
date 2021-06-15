package com.lodenou.go4lunch.controller.fragments.listview;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.ApiCall;
import com.lodenou.go4lunch.model.autocomplete.Prediction;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class ListViewFragment extends Fragment implements ApiCall.Callbacks, ApiCall.Callbacks3, ApiCall.Callbacks2 {


    RecyclerView mRecyclerView;
    ListViewRecyclerViewAdapter mAdapter;
    SearchListAdapter mSearchListAdapter;
    private List<Result> mRestaurants;
    String location1 = "";
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private List<com.lodenou.go4lunch.model.detail.Result> mResultList = new ArrayList<>();


    public ListViewFragment() {

    }


    public static ListViewFragment newInstance() {
        ListViewFragment listViewFragment = new ListViewFragment();
        return listViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view_list, container, false);
        setRecyclerView(view);


        return view;
    }

    // searchview //////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("onQueryTextChange", newText);
                if (newText.length() == 0){
                    executeHttpRequestWithRetrofit();
                    mRecyclerView.setAdapter(mAdapter);
                }
                if (newText.length() >= 3) {
                    executeHttpRequestWithRetrofit2(newText);

                }
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("onQueryTextSubmit", query);

                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResponse2(@Nullable List<Prediction> predictions) {
        if (predictions != null) {
            mResultList.clear();
            for (Prediction p : predictions) {
                for (String s : p.getTypes()) {
                    if (s.equals("restaurant")) {
                    getCallbackFetchDetail(p.getPlaceId());
                }
                }
            }
            Log.d("TAG", "onResponse2: ");
        }
    }

    @Override
    public void onFailure2() {

    }

    private void executeHttpRequestWithRetrofit2(String input) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                final Double currentLat = location.getLatitude();
                final Double currentLng = location.getLongitude();
                location1 = currentLat.toString() + "," + currentLng.toString();
                getCallbackAutocomplete(input);
            }
        });
    }
    private void getCallbackAutocomplete(String input){
        ApiCall.autocompleteSearch(this, location1, 5000, input);
    }



    private void getCallbackFetchDetail(String placeId){
        ApiCall.fetchDetail(this, placeId);
    }

    @Override
    public void onResponse(com.lodenou.go4lunch.model.detail.Result result) {
//            if (mResultList == mRestaurants)
            mResultList.add(result);
            setSearchListAdapter();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public void onResume() {
        super.onResume();
        executeHttpRequestWithRetrofit();
    }

    private void setRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_list_view);

        this.mRestaurants = new ArrayList<>();

        this.mAdapter = new ListViewRecyclerViewAdapter(mRestaurants);
        mRecyclerView.setAdapter(this.mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void setSearchListAdapter(){

        this.mSearchListAdapter = new SearchListAdapter(mResultList);
        mRecyclerView.setAdapter(this.mSearchListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    // 4 - Execute HTTP request and update UI

    private void executeHttpRequestWithRetrofit() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                final Double currentLat = location.getLatitude();
                final Double currentLng = location.getLongitude();
                location1 = currentLat.toString() + "," + currentLng.toString();
                getCallBack();
            }
        });

    }

    public void getCallBack() {
        ApiCall.fetchRestaurantsNear(this, location1, 5000);
    }

    @Override
    public void onResponse(@Nullable List<Result> results) {
        mRestaurants.clear();
        mRestaurants.addAll(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure() {

    }
}