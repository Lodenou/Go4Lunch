package com.lodenou.go4lunch.controller.activity.yourlunchactivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.model.User;

import java.util.List;

public class YourLunchAdapter extends RecyclerView.Adapter<YourLunchViewHolder> {


    private List<User> mUsers;

    public YourLunchAdapter(List<User> users) {
        this.mUsers = users;
    }

    @NonNull
    @Override
    public YourLunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.fragment_list_view_users, parent, false);

        // Return a new holder instance
        YourLunchViewHolder viewHolder = new YourLunchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull YourLunchViewHolder holder, int position) {
        User user = mUsers.get(position);




        TextView usernameLunch = holder.mUsernameLunch;
        // change text color to black to fit with the white bg
        usernameLunch.setTextColor(Color.argb(255, 0, 0, 0));
        usernameLunch.setText(user.getName());
        TextView userChoice = holder.mUserChoice;
        userChoice.setText(" is joining !");
//         change text color to black to fit with the white bg
        userChoice.setTextColor(Color.argb(255, 0, 0, 0));

        ImageView userAvatar = holder.mAvatarUserLunch;
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
