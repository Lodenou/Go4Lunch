package com.lodenou.go4lunch.controller.fragments.workmates;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lodenou.go4lunch.R;

public class WorkmatesViewHolder extends RecyclerView.ViewHolder {

    public final TextView mIdView;
    public final TextView mContentView;

    public WorkmatesViewHolder(@NonNull View itemView) {
        super(itemView);
        mIdView = (TextView) itemView.findViewById(R.id.item_number);;
        mContentView = (TextView) itemView.findViewById(R.id.content);
    }
}
