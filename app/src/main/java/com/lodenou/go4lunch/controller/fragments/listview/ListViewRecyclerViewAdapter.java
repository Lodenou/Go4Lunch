package com.lodenou.go4lunch.controller.fragments.listview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.util.List;
public class ListViewRecyclerViewAdapter extends RecyclerView.Adapter<ListViewViewHolder> {


    private List<Result> mRestaurants;

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

    @Override
    public void onBindViewHolder(@NonNull ListViewViewHolder holder, int position) {
        Result restaurant = mRestaurants.get(position);

        TextView textView = holder.mContentView;
        TextView textView1 = holder.mIdView;

        textView.setText(restaurant.getName());
        //TODO getimage etc
        textView1.setText(String.valueOf(position));
    }


    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }
}