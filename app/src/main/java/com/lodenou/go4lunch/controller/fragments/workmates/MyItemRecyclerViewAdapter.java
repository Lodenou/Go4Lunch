package com.lodenou.go4lunch.controller.fragments.workmates;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.activitiy.yourlunchactivity.YourLunchActivity;
import com.lodenou.go4lunch.model.User;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<WorkmatesViewHolder> {

    private List<User> mUsers;


    public MyItemRecyclerViewAdapter(List<User> users) {
        this.mUsers = users;
    }


    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.fragment_list_view_users, parent, false);

        // Return a new holder instance
        WorkmatesViewHolder viewHolder = new WorkmatesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position) {


        // Get the data model based on position
        String displayedText = "";
        final User user = mUsers.get(position);

        displayedText+=user.getName();
        // Set item views based on your views and data model

        if (user.getRestaurantPlaceId() != null && !(user.getRestaurantPlaceId().isEmpty())) {
            displayedText+=" is eating at "+user.getRestaurantName();

        TextView userRestaurant = holder.mUserRestaurant;
        if (user.getRestaurantName() != null && !user.getRestaurantName().equals("")) {
            userRestaurant.setText(" is eating at " + user.getRestaurantName());
        } else {
            userRestaurant.setText(" hasn't decided yet");
        }


        ImageView userAvatar = holder.mAvatarUser;
        String avatarUrl = user.getAvatarUrl();
        if (avatarUrl != null) {
            Context context = userAvatar.getContext();
            Glide.with(context)
                    .load(avatarUrl)
                    .sizeMultiplier(0.08f)
                    .circleCrop()
                    .into(userAvatar);
        }
        else {
            Context context = userAvatar.getContext();
            Glide.with(context)
                    .load("https://fnadepape.org/wp-content/uploads/2018/07/avatar-1577909_960_720.png")
                    .sizeMultiplier(0.08f)
                    .circleCrop()
                    .into(userAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}