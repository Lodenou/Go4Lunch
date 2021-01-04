package com.lodenou.go4lunch.controller.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.ApiCall;
import com.lodenou.go4lunch.controller.api.ApiClient;
import com.lodenou.go4lunch.model.nearbysearch.Restaurant;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsFragment extends Fragment implements ApiCall.Callbacks {


    private static final int RC_CAMERA_AND_LOCATION = 1;
    MapsFragment fragmentMap;
    GoogleMap mGoogleMap;


    public static MapsFragment newInstance() {
        MapsFragment fragmentMap = new MapsFragment();
        return fragmentMap;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(final GoogleMap googleMap) {
            methodRequiresTwoPermission(googleMap);


        }

    };

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission(final GoogleMap googleMap) {
        mGoogleMap = googleMap;

        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_CAMERA_AND_LOCATION, perms);
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
            Task<Location> task = fusedLocationProviderClient.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        final Double currentLat = location.getLatitude();
                        final Double currentLng = location.getLongitude();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLng), 15));
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLng)));

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);
                        executeHttpRequestWithRetrofit(currentLat, currentLng);
                    }
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    // 4 - Execute HTTP request and update UI
    private void executeHttpRequestWithRetrofit(Double lat, Double lng) {
        String location = lat.toString() + "," + lng.toString();
        ApiCall.fetchRestaurantsNear(this, location, 5000);
    }


    @Override
    public void onResponse(@Nullable List<Result> results) {
        Log.d("error5555", "on response");
        //TODO
        if (results != null) {
            int i = 0;
            while(i < results.size()) {

                double currentLat = results.get(i).getGeometry().getLocation().getLat();
                double currentLng = results.get(i).getGeometry().getLocation().getLng();

                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLng)));
                i++;

            }
        }
    }

    @Override
    public void onFailure() {
        Log.d("error", "on failure");
    }


    // 3 - Update UI showing only name of users
    private void updateUIWithResult(List<Result> results){
            
        }
    }


