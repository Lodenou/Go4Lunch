package com.lodenou.go4lunch.controller.activity.yourlunchactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lodenou.go4lunch.BuildConfig;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.ApiCall;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.User;
import com.lodenou.go4lunch.model.nearbysearch.Restaurant;
import com.lodenou.go4lunch.model.nearbysearch.Result;

import java.util.ArrayList;
import java.util.List;

public class YourLunchActivity extends AppCompatActivity implements ApiCall.Callbacks2 {

    User mUser = new User();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<User> mUsers;
    private List<Result> mRestaurants;
    private Restaurant mRestaurant;
    private com.lodenou.go4lunch.model.detail.Result mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(Color.parseColor("#544554"));
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setBackgroundColor(Color.parseColor("#800000"));
        getCurrentUser();
        setUpRecyclerView();
    }


    //TODO "TRANSFORM QUERY TO USER" COMME DANS WORKMATE FRAGMENT
    // PERMET D AFFICHER TOUTE LA LISTE DE WORKMATES ET ENSUITE PERMET  DE TRIER

    private void setFavoriteRestaurant(String value) {
        UserHelper.updateUser(value, FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    private void unsetFavoriteRestaurant(String value) {
        UserHelper.updateUser(value, FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    //TODO A AMELIORER
    private void recordUserRestaurant(){
        String value = getIntent().getStringExtra("key");
        UserHelper.updateUserWithRestaurantInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(),value, mResult.getName(), mResult.getVicinity());
    }

    private void removeUserRestaurant(){

        UserHelper.updateUserWithRestaurantInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(),"", "", "");
    }

    private void getCurrentUser() {
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser != null) {
            String currentUserID = mFirebaseUser.getUid();
            UserHelper.getUser(currentUserID)
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            mUser = documentSnapshot.toObject(User.class);
                            getCallBack();
                        }
                    });
        }
    }


    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // Improve performance if the size of the recyclerview won't change
        mRecyclerView.setHasFixedSize(true);
        //Linear layout manager
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        this.mUsers = new ArrayList<>();
        transformQuerytoUser();
        // Specify the adapter
        mAdapter = new YourLunchAdapter(mUsers);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void transformQuerytoUser(){
        UserHelper.getAllUsers().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mUsers.clear();
                mAdapter.notifyDataSetChanged();
                List<DocumentSnapshot> listworkmates = queryDocumentSnapshots.getDocuments();
                String value = getIntent().getStringExtra("key");
                for (DocumentSnapshot item: listworkmates) {
                    User userw =  item.toObject(User.class);
                    if (userw.getRestaurantPlaceId() != null && userw.getRestaurantPlaceId().equals(value)) {
                        mUsers.add(userw);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void getCallBack() {
        final ImageView imageStar = findViewById(R.id.image_star);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        String value = getIntent().getStringExtra("key");

        if (mUser.getFavoritesRestaurants() == null || !(mUser.getFavoritesRestaurants().contains(value))) {
            imageStar.setVisibility(View.INVISIBLE);
        } else {
            imageStar.setVisibility(View.VISIBLE);
        }


        if (mUser.getRestaurantPlaceId() != null && mUser.getRestaurantPlaceId().equals(value)) {
            fab.setImageResource(R.drawable.ic_baseline_check_circle_24);
            fab.setColorFilter(Color.argb(250, 25, 255, 25));
        }

        else {
            fab.setImageResource(R.drawable.ic_baseline_crop_din_24);
        }

        ApiCall.fetchDetail(this, value);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onResponse(final com.lodenou.go4lunch.model.detail.Result result) {

        final TextView restaurantName = findViewById(R.id.restaurant_name2);
        TextView restaurantAddress = findViewById(R.id.restaurant_address2);
        ImageView restaurantImage = findViewById(R.id.restaurant_image2);
        Button phoneButton = findViewById(R.id.call_button);
        Button websiteButton = findViewById(R.id.website_button);
        Button starButton = findViewById(R.id.star_button);
        final ImageView imageStar = findViewById(R.id.image_star);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mResult = result;
        final String value = getIntent().getStringExtra("key");


        restaurantName.setText(result.getName());
        restaurantAddress.setText(result.getVicinity());
        Glide.with(getApplicationContext())
                .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference="
                        + result.getPhotos().get(0).getPhotoReference() + "&key=" + BuildConfig.GOOGLE_MAP_API_KEY)
                .into(restaurantImage);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = result.getWebsite();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mUser.getFavoritesRestaurants() == null || !(mUser.getFavoritesRestaurants().contains(value))) {
                    setFavoriteRestaurant(value);
                    mUser.getFavoritesRestaurants().add(value);
                    imageStar.setVisibility(View.VISIBLE);
                    Snackbar.make(v, "Ajouté aux favoris", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    unsetFavoriteRestaurant(value);
                    mUser.getFavoritesRestaurants().remove(value);
                    imageStar.setVisibility(View.INVISIBLE);
                    Snackbar.make(v, "Retiré des favoris", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mUser.getRestaurantPlaceId() == null || !(mUser.getRestaurantPlaceId().equals(value))) {
                    mUser.setRestaurantPlaceId(value);
                    recordUserRestaurant();
                    Snackbar.make(view, "Vous allez manger au restaurant "+result.getName(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_baseline_check_circle_24);
                    fab.setColorFilter(Color.argb(250, 25, 255, 25));
                }

                else {
                    mUser.setRestaurantPlaceId("");
                    removeUserRestaurant();
                    fab.setImageResource(R.drawable.ic_baseline_crop_din_24);
                    Snackbar.make(view, "Vous ne mangez plus au restaurant "+result.getName(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                transformQuerytoUser();
            }
        });

    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:12345678901")));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
}