package com.lodenou.go4lunch.controller.fragments.workmates;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.activity.MainActivity;
import com.lodenou.go4lunch.controller.activity.yourlunchactivity.YourLunchActivity;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.User;

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


//    private void startYourLunchActivityOnClick(){
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, YourLunchActivity.class);
//                context.startActivity(intent);
//            }
//        });
//    }

    private void startYourLunchOnClick() {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User mUser = new User();

                //TODO REUSSIR A ENVOYER LE RESTAURANTPLACEID
                Context context = view.getContext();
                Intent intent = new Intent(context, YourLunchActivity.class);
                intent.putExtra("key", mUser.getRestaurantPlaceId());
                context.startActivity(intent);
            }
        });
    }
}