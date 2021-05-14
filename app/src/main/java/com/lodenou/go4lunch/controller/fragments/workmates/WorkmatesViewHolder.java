package com.lodenou.go4lunch.controller.fragments.workmates;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.activity.yourlunchactivity.YourLunchActivity;

public class WorkmatesViewHolder extends RecyclerView.ViewHolder {

    public final ImageView mAvatarUser;
    public final TextView mUsername;
    public final TextView mUserRestaurant;

    public WorkmatesViewHolder(@NonNull View itemView) {
        super(itemView);
        mIdView = (TextView) itemView.findViewById(R.id.item_number);;
        mContentView = (TextView) itemView.findViewById(R.id.content);
//        startYourLunchActivityOnClick();
    }


    private void startYourLunchActivityOnClick(){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, YourLunchActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
