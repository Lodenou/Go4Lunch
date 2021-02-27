package com.lodenou.go4lunch.controller.activitiy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.PageAdapter;
import com.lodenou.go4lunch.controller.activitiy.yourlunchactivity.YourLunchActivity;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.controller.fragments.MapsFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private int[] tabIcons = {
            R.drawable.ic_baseline_map_24,
            R.drawable.ic_baseline_view_list_24,
            R.drawable.ic_baseline_people_24
    };
    GoogleSignInClient mGoogleApiClient;
    private final String TAG = "cycle";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGoogleAccountInformation();
        createNavMenu();
        configureViewPagerAndTabs();
        setTabIcons();
        setNavMenuOnClicks();
        getFbInfo();
        createUserInFirestore();
    }

    @Override
    protected void onDestroy() {
        logOut4();
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    private void configureViewPagerAndTabs() {

        ViewPager pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));


        TabLayout tabs = (TabLayout) findViewById(R.id.tab_layout);
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
        final ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("I'm hungry !");
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: toolbar.setTitle("I'm hungry !");
                    break;
                    case 1: toolbar.setTitle("I'm hungry !");
                    break;
                    case 2: toolbar.setTitle("Available workmates");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabIcons() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void createNavMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
        };
    }

    private void getFbInfo() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject json, GraphResponse response) {
                // Application code
                if (response.getError() != null) {
                    System.out.println("ERROR");
                } else {
                    System.out.println("Success");
                    String jsonresult = String.valueOf(json);
                    System.out.println("JSON Result" + jsonresult);

                    String fbUserId = json.optString("id");
                    String fbUserFirstName = json.optString("name");
                    String fbUserEmail = json.optString("email");
                    String fbUserProfilePics = "https://graph.facebook.com/" + fbUserId + "/picture?type=large";
                    Log.d("SignUpActivity", "Email: " + fbUserEmail + "\nName: " + fbUserFirstName + "\nID: " + fbUserId);

                    // USE THIS INFOS INTO NAVIGATION MENU
                    View headerView = ((NavigationView) findViewById(R.id.nav_view))
                            .getHeaderView(0);

                    TextView username = headerView
                            .findViewById(R.id.textview_user_name);
                    TextView emailadress = headerView
                            .findViewById(R.id.textView);
                    ImageView personalphoto = headerView
                            .findViewById(R.id.imageView);

                    username.setText(fbUserFirstName);
                    emailadress.setText(fbUserEmail);
                    if (personalphoto != null) {
                        Glide.with(headerView)
                                .load(fbUserProfilePics)
                                .circleCrop()
                                .into(personalphoto);
                    }

                }

                Log.d("SignUpActivity", response.toString());
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void logOut4() {
        // FIREBASE LOGOUT
        FirebaseAuth.getInstance().signOut();
        // FACEBOOK LOGOUT
        LoginManager.getInstance().logOut();
        // GOOGLE LOGOUT
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        googleSignInClient.signOut();
        Intent intent3 = new Intent(MainActivity.this, ConnexionActivity.class);
        finish();
        startActivity(intent3);
    }

    private void setNavMenuOnClicks() {
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_yourlunch:
                        Intent intent = new Intent(MainActivity.this, YourLunchActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_settings:
                        Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.nav_logout:
                        logOut4();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void getGoogleAccountInformation() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            if (personName != null) {

                View headerView = ((NavigationView) findViewById(R.id.nav_view))
                        .getHeaderView(0);

                TextView username = headerView
                        .findViewById(R.id.textview_user_name);
                TextView emailadress = headerView
                        .findViewById(R.id.textView);
                ImageView personalphoto = headerView.findViewById(R.id.imageView);

                Glide.with(this)
                        .load(personPhoto)
                        .sizeMultiplier(0.1f)
                        .circleCrop()
                        .into(personalphoto);
                username.setText(personName);
                emailadress.setText(personEmail);

            }
        }
    }

    @Nullable
    protected FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected Boolean isCurrentUserLogged(){
        return (this.getCurrentUser() != null);
    }

    protected OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Firestore Error", Toast.LENGTH_LONG).show();
            }
        };
    }



    // 1 - Http request that create user in firestore
    private void createUserInFirestore(){
        if (this.getCurrentUser() != null){

            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
            String username = this.getCurrentUser().getDisplayName();
            String uid = this.getCurrentUser().getUid();

            UserHelper.createUser(uid, username, null, null, null, urlPicture,null).addOnFailureListener(this.onFailureListener());
        }
    }

}