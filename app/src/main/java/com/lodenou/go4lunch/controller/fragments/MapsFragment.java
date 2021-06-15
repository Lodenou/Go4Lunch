package com.lodenou.go4lunch.controller.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.ApiCall;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.User;
import com.lodenou.go4lunch.model.autocomplete.Prediction;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsFragment extends Fragment implements ApiCall.Callbacks, ApiCall.Callbacks2, ApiCall.Callbacks3 {


    private static final int RC_CAMERA_AND_LOCATION = 1;
    GoogleMap mGoogleMap;
    List<Result> mResults;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    String location1 = "";
    private List<com.lodenou.go4lunch.model.detail.Result> mResultList = new ArrayList<>();


    public static MapsFragment newInstance() {
        MapsFragment fragmentMap = new MapsFragment();
        return fragmentMap;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
            mGoogleMap = googleMap;
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
//                        googleMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLng)));

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
                if (newText.length() == 0) {

                    FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return true;
                    }
                    Task<Location> task = fusedLocationProviderClient.getLastLocation();
                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            final Double currentLat = location.getLatitude();
                            final Double currentLng = location.getLongitude();
                            location1 = currentLat.toString() + "," + currentLng.toString();
                            executeHttpRequestWithRetrofit(currentLat, currentLng);
                        }
                    });
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

    private void getCallbackAutocomplete(String input) {
        ApiCall.autocompleteSearch(this, location1, 5000, input);
    }

    private void getCallbackFetchDetail(String placeId) {
        ApiCall.fetchDetail(this, placeId);
    }


    @Override
    public void onFailure() {
        Log.d("error", "on failure");
    }

    @Override
    public void onResponse(com.lodenou.go4lunch.model.detail.Result result) {
        final double currentLat = result.getGeometry().getLocation().getLat();
        final double currentLng = result.getGeometry().getLocation().getLng();

        // resize the marker
        final int height = 100;
        final int width = 70;
        mResultList.add(result);
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_orange);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        // add orange markers
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(currentLat, currentLng))
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }


    @Override
    public void onResponse2(List<Prediction> predictions) {
        if (predictions != null) {
            mResultList.clear();
            mGoogleMap.clear();
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
////////////////////////////////////////////////////////////////////////////////////////////////////


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
    public void onResponse(@Nullable final List<Result> results) {
        Log.d("error5555", "on response");
        mResults = results;
        //TODO
        if (results != null) {
            mGoogleMap.clear();
            int i = 0;
            while (i < results.size()) {

                final double currentLat = results.get(i).getGeometry().getLocation().getLat();
                final double currentLng = results.get(i).getGeometry().getLocation().getLng();

                // resize the marker
                final int height = 100;
                final int width = 70;

                final int finalI = i;

                UserHelper.getAllUsers().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Boolean choose = false;
                        List<DocumentSnapshot> listworkmates = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot item : listworkmates) {
                            User userw = item.toObject(User.class);

                            if (userw.getRestaurantPlaceId() != null && userw.getRestaurantPlaceId().equals(results.get(finalI).getPlaceId())) {
                                choose = true;
                                break;
                            }
                        }
                        if (choose.equals(true)) {
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_white);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                            // add orange markers
                            mGoogleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(currentLat, currentLng))
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                        } else {
                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_marker_orange);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                            // add orange markers
                            mGoogleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(currentLat, currentLng))
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        }
                    }
                });
                i++;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleMap != null) {
            onResponse(mResults);
        }
    }


    // 3 - Update UI showing only name of users
    private void updateUIWithResult(List<Result> results) {

    }

}


