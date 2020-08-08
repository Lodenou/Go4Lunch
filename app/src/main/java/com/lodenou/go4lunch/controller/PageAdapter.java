package com.lodenou.go4lunch.controller;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lodenou.go4lunch.controller.fragments.listview.ListViewFragment;
import com.lodenou.go4lunch.controller.fragments.MapsFragment;
import com.lodenou.go4lunch.controller.fragments.workmates.WorkMatesFragment;

public class PageAdapter extends FragmentPagerAdapter {



    // Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return 3; // 3 - Number of page to show
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MapsFragment.newInstance();
        }

        else if(position == 1) {
            return ListViewFragment.newInstance();
        }
        else   {
            return WorkMatesFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {


        switch (position) {
            case 0: //Page number 1
                return "Map View";
            case 1: //Page number 2
                return "List View";
            case 2: //Page number 3
                return "Workmates";
            default:
                return null;
        }
    }


}


