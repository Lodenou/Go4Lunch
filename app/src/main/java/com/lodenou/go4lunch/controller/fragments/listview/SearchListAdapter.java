package com.lodenou.go4lunch.controller.fragments.listview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lodenou.go4lunch.BuildConfig;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.activity.yourlunchactivity.YourLunchActivity;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.User;
import com.lodenou.go4lunch.model.detail.Result;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<ListViewViewHolder> {

    List<Result> mResultList = new ArrayList<>();
    Context context;

    public SearchListAdapter(List<Result> resultList) {
        mResultList = resultList;
    }

    @NonNull
    @Override
    public ListViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_list_view, parent, false);

        ListViewViewHolder viewHolder = new ListViewViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewViewHolder holder, int position) {
        final Result restaurant = mResultList.get(position);

        TextView restaurantName = holder.mContentView;
        TextView restaurantAddress = holder.mRestaurantAddress;
        TextView openingHours = holder.mOpeningHours;
        final TextView distance = holder.mDistance;
        final TextView workmatesNumber = holder.mWorkmatesNumber;
        TextView ratingStars = holder.mRatingStars;
        ImageView restaurantImage = holder.mRestaurantImage;


        restaurantName.setText(restaurant.getName());
        restaurantAddress.setText(restaurant.getVicinity());

        if (restaurant.getOpeningHours() != null) {
            if (restaurant.getOpeningHours().getOpenNow()) {
                openingHours.setText("Open");
            } else {
                openingHours.setText("Closed");
            }
        } else {
            openingHours.setText("Pas d'horaire spécifié");
        }
        //TODO distance à calculer
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                .PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                final Double currentLat = location.getLatitude();
                final Double currentLng = location.getLongitude();
                String positionResto = restaurant.getGeometry().getLocation().getLat().toString() + "," + restaurant
                        .getGeometry().getLocation().getLng().toString();
                String position = currentLat.toString() + "," + currentLng.toString();

                Location locationResto = new Location(LocationManager.GPS_PROVIDER);
                locationResto.setLatitude(restaurant.getGeometry().getLocation().getLat());
                locationResto.setLongitude(restaurant.getGeometry().getLocation().getLng());
                Float distance1 = location.distanceTo(locationResto);
                int i = distance1.intValue();
                distance.setText(i + " m");
            }
        });

        //TODO workmatesnumber
        UserHelper.getAllUsers().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int i = 0;
                List<DocumentSnapshot> listworkmates = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot item : listworkmates) {
                    User userw = item.toObject(User.class);
                    if (userw.getRestaurantPlaceId() != null && userw.getRestaurantPlaceId().equals(restaurant.getPlaceId())) {
                        i = i + 1;
                    }

//                    mUsers.add(userw);
//                    mAdapter.notifyDataSetChanged();
                }
                workmatesNumber.setText(String.valueOf(i));
            }
        });

        if (restaurant.getRating() != null) {
            ratingStars.setText(restaurant.getRating().toString());
        }

        if (restaurant.getPhotos() != null && restaurant.getPhotos().size() > 0) {
            Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference=" + restaurant.getPhotos().get(0).getPhotoReference() + "&key=" + BuildConfig.GOOGLE_MAP_API_KEY)
                    .into(restaurantImage);
        } else {

        }

        //TODO onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, YourLunchActivity.class);
                intent.putExtra("key", restaurant.getPlaceId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    private void updateList(List<Result> resultList) {
        mResultList.clear();
        mResultList.addAll(resultList);
        notifyDataSetChanged();
    }
}
