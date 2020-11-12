package com.lodenou.go4lunch.controller.activitiy.yourlunchactivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import java.util.Objects;

public class YourLunchActivity extends AppCompatActivity {

    User mUser= new User();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(Color.parseColor("#80000000"));
//        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setBackgroundColor(Color.parseColor("#800000"));
//        toolBarLayout.setTitle(getTitle());
        getCurrentUser();
        fabClick();
        setUpRecyclerView();

    }

    private void setFavoriteRestaurant(){
        UserHelper.updateUser(true,FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void unsetFavoriteRestaurant(){
        UserHelper.updateUser(false,FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void getCurrentUser(){



//        UserHelper.getUser(mUser2.getUid())
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                mUser = documentSnapshot.toObject(User.class);
//            }
//        });

        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mFirebaseUser != null) {
          String  currentUserID = mFirebaseUser.getUid();
            UserHelper.getUser(currentUserID)
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mUser = documentSnapshot.toObject(User.class);
            }
        });
}



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

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // Improve performance if the size of the recyclerview won't change
        mRecyclerView.setHasFixedSize(true);
        //Linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // Specify the adapter
        mAdapter = new YourLunchAdapter(mUsers);
        mRecyclerView.setAdapter(mAdapter);
    }

}