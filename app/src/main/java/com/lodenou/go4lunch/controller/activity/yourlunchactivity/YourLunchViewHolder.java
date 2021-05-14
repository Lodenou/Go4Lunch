package com.lodenou.go4lunch.controller.activity.yourlunchactivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lodenou.go4lunch.R;

public class YourLunchViewHolder extends RecyclerView.ViewHolder {

    public final ImageView mAvatarUserLunch;
    public final TextView mUsernameLunch;
    public final TextView mUserChoice;

    public YourLunchViewHolder(@NonNull View itemView) {
        super(itemView);
        mAvatarUserLunch = itemView.findViewById(R.id.user_avatar);;
        mUsernameLunch = itemView.findViewById(R.id.user_name);
        mUserChoice = itemView.findViewById(R.id.user_restaurant);
    }
}
