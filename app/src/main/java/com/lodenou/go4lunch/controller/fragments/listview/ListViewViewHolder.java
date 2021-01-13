package com.lodenou.go4lunch.controller.fragments.listview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.activitiy.yourlunchactivity.YourLunchActivity;

public class ListViewViewHolder extends RecyclerView.ViewHolder {

    public final TextView mIdView;
    public final TextView mContentView;


    public ListViewViewHolder(@NonNull View itemView) {
        super(itemView);

        mIdView = (TextView) itemView.findViewById(R.id.item_number);
        mContentView = (TextView) itemView.findViewById(R.id.content);
        startLunchActivityOnClick();
    }


    private void startLunchActivityOnClick(){
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
