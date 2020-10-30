package com.lodenou.go4lunch.controller.activitiy;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.UserData;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.User;

import java.util.List;

public class YourLunchActivity extends AppCompatActivity {

    User mUser= new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        getCurrentUser();
        fabClick();

    }

    private void setFavoriteRestaurant(){
        UserHelper.updateUser(true,FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void unsetFavoriteRestaurant(){
        UserHelper.updateUser(false,FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void getCurrentUser(){
        UserHelper.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mUser = documentSnapshot.toObject(User.class);
            }
        });
    }

    private void fabClick(){

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mUser.haveFavoriteRestaurant()) {
                    mUser.setFavorite(true);
                    setFavoriteRestaurant();
                    Snackbar.make(view, "Ajouté aux favoris", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    //TODO changer la couleur en #3DDC84
//                    fab.setImageTintList(ColorStateList.valueOf());
                    fab.setColorFilter(Color.argb(250, 25, 255, 25));
                }

                else {
                    mUser.setFavorite(false);
                    unsetFavoriteRestaurant();
                    fab.setImageResource(R.drawable.ic_baseline_crop_din_24);
                    Snackbar.make(view, "Retiré des favoris", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}