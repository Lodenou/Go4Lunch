package com.lodenou.go4lunch;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lodenou.go4lunch.fragments.ListView.ListViewFragment;
import com.lodenou.go4lunch.fragments.MapsFragment;
import com.lodenou.go4lunch.fragments.workmates.WorkMatesFragment;

public class PageAdapter extends FragmentPagerAdapter {

    // 1 - Array of colors that will be passed to PageFragment
    private int[] colors;

    // 2 - Default Constructor
    public PageAdapter(FragmentManager mgr, int[] colors) {
        super(mgr);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return 3; // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        int fragment_page = R.layout.fragment_page;
//        switch (position) {
//            case 0:
//                fragment_page = R.layout.fragment_page;
//                break;
//            case 1:
//                fragment_page = R.layout.fragment_page;
//                break;
//            case 2:
//                fragment_page = R.layout.fragment_page;
//                break;
//            default:
//                fragment_page = R.layout.fragment_page;
//        }
//        // 4 - Page to return
//        return (this.newInstance(position, this.colors[position]));
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


