package com.lodenou.go4lunch.controller.fragments.listview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.activitiy.yourlunchactivity.YourLunchActivity;

public class ListViewViewHolder extends RecyclerView.ViewHolder {


    public final TextView mContentView;
    public final TextView mRestaurantAddress;
    public final TextView mOpeningHours;
    public final TextView mDistance;
    public final TextView mWorkmatesNumber;
    public final TextView mRatingStars;
    public final ImageView mRestaurantImage;


    public ListViewViewHolder(@NonNull View itemView) {
        super(itemView);


        mContentView = (TextView) itemView.findViewById(R.id.restaurant_name);
        mRestaurantAddress = itemView.findViewById(R.id.restaurant_address);
        mOpeningHours = itemView.findViewById(R.id.opening_hours);
        mDistance = itemView.findViewById(R.id.distance);
        mWorkmatesNumber = itemView.findViewById(R.id.workmates_number);
        mRatingStars = itemView.findViewById(R.id.rating_stars);
        mRestaurantImage = itemView.findViewById(R.id.restaurant_image);



//        startLunchActivityOnClick();
    }


//    private void startLunchActivityOnClick(){
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Context context = v.getContext();
//                Intent intent = new Intent(context, YourLunchActivity.class);
//                //TODO envoyer le nom du restaurant selectionné dans la liste
////                intent.putExtra("key", res)
//                context.startActivity(intent);
//            }
//        });
//    }
}
