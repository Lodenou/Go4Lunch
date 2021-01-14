package com.lodenou.go4lunch.controller.fragments.workmates;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodenou.go4lunch.R;
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
        User user = mUsers.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.mContentView;
        textView.setText(user.getName());

        TextView textView1 = holder.mIdView;
        textView1.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}