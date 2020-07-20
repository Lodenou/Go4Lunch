package com.lodenou.go4lunch;


import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
            return(3); // 3 - Number of page to show
        }

        @Override
        public Fragment getItem(int position) {
            // 4 - Page to return
            return(PageFragment.newInstance(position, this.colors[position]));
        }
    @Override
    public CharSequence getPageTitle(int position) {


        switch (position){
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

