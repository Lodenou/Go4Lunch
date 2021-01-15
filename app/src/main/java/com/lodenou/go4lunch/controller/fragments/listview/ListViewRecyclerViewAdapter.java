package com.lodenou.go4lunch.controller.fragments.listview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
public class ListViewRecyclerViewAdapter extends RecyclerView.Adapter<ListViewViewHolder> {


    private List<Result> mRestaurants;
    Context context;

    public ListViewRecyclerViewAdapter(List<Result> restaurants) {
        this.mRestaurants = restaurants;

    }


    @NonNull
    @Override
    public ListViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.fragment_list_view, parent, false);

        ListViewViewHolder viewHolder = new ListViewViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewViewHolder holder, int position) {
        Result restaurant = mRestaurants.get(position);

        TextView restaurantName = holder.mContentView;
        TextView restaurantAddress = holder.mRestaurantAddress;
        TextView openingHours = holder.mOpeningHours;
        TextView distance = holder.mDistance;
        TextView workmatesNumber = holder.mWorkmatesNumber;
        TextView ratingStars = holder.mRatingStars;
        ImageView restaurantImage = holder.mRestaurantImage;


        restaurantName.setText(restaurant.getName());
        restaurantAddress.setText(restaurant.getVicinity());
        openingHours.setText(restaurant.getBusinessStatus());
        //TODO distance à calculer
        distance.setText(restaurant.getScope());
        //TODO workmatesnumber
        workmatesNumber.setText(restaurant.getUserRatingsTotal().toString());
        ratingStars.setText(restaurant.getRating().toString());

        //TODO image du restaurant
//        restaurantImage.setImageBitmap(restaurant.getPhotos().get(0).getPhotoReference());
//        Glide.with(context).load(restaurant.getIcon()).into(restaurantImage);



        //TODO getimage etc
        //TODO onclick
//        textView1.setText(String.valueOf(position));
    }


    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }
}