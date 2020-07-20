package com.lodenou.go4lunch;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.net.URI;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private int[] tabIcons = {
            R.drawable.ic_baseline_map_24,
            R.drawable.ic_baseline_view_list_24,
            R.drawable.ic_baseline_people_24
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGoogleAccountInformation();
        createNavMenu();
        configureViewPagerAndTabs();
        setTabIcons();
        setNavMenuOnClicks();
    }

    private void configureViewPagerAndTabs(){

        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager)));


        TabLayout tabs = (TabLayout)findViewById(R.id.tab_layout);
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void setTabIcons() {

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);


    }

    private void createNavMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        toolbar.setTitle("I'm Hungry!");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){
        };
    }

    private void setNavMenuOnClicks() {
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_yourlunch:
                        Intent intent = new Intent(MainActivity.this, YourLunchActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_settings:
                        Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.nav_logout:
                    //TODO
                        return true;
                    default:
                        return false;
            }}
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

            if(personName != null){

                View headerView = ((NavigationView) findViewById(R.id.nav_view))
                        .getHeaderView(0);

                TextView username = headerView
                        .findViewById(R.id.textview_user_name);
                TextView emailadress = headerView
                        .findViewById(R.id.textView);
                ImageView personalphoto = headerView.findViewById(R.id.imageView);

                //FIXME à vérifier si cela marche vraiment avec toutes les images de profil
                Glide.with(this)
                        .load(personPhoto)
                        .sizeMultiplier(0.1f)
                        .circleCrop()
                        .into(personalphoto);
                username.setText(personName);
                emailadress.setText(personEmail);

        }}}

}