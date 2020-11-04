package com.lodenou.go4lunch.controller.activitiy.yourlunchactivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.fragments.workmates.WorkmatesViewHolder;
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
        View view = inflater.inflate(R.layout.fragment_list_view, parent, false);

        // Return a new holder instance
        YourLunchViewHolder viewHolder = new YourLunchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull YourLunchViewHolder holder, int position) {
//        User user = mUsers.get(position);

        TextView textView = holder.mContentView;
        // change text color to black to fit with the white bg
//        textView.setTextColor(Color.argb(255, 0, 0, 0));
//        textView.setText(user.getName());
        TextView textView1 = holder.mIdView;
        textView1.setText(String.valueOf(position));
        // change text color to black to fit with the white bg
        textView1.setTextColor(Color.argb(255, 0, 0, 0));
    }

    @Override
    public int getItemCount() {
//        return mUsers.size();
        return 5;
    }
}
