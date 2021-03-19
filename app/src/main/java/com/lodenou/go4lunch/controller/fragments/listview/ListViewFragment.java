package com.lodenou.go4lunch.controller.fragments.listview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.ApiCall;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class ListViewFragment extends Fragment implements ApiCall.Callbacks {


    RecyclerView mRecyclerView;
    ListViewRecyclerViewAdapter mAdapter;
    private List<Result> mRestaurants;
    String location1 = "";


    public ListViewFragment() {

    }


    public static ListViewFragment newInstance() {
        ListViewFragment listViewFragment = new ListViewFragment();
        return listViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view_list, container, false);
        setRecyclerView(view);


        return view;
    }

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