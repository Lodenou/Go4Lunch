package com.lodenou.go4lunch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lodenou.go4lunch.fragments.MapsFragment;


public class PageFragment extends Fragment {

    public static PageFragment newInstance() {
        PageFragment fragmentPage = new PageFragment();
        return fragmentPage;
    }
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String KEY_POSITION = "position";
//    private static final String KEY_COLOR = "color";
//
//
//    public PageFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @return A new instance of fragment BlankFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PageFragment newInstance(int position, int color) {
//        PageFragment fragment = new PageFragment();
//        Bundle args = new Bundle();
//        args.putInt(KEY_POSITION, position);
//        args.putInt(KEY_COLOR, color);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        int position = getArguments().getInt(KEY_POSITION, -1);
//
//        int fragment_page = R.layout.fragment_page;
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
//        }
//        View result = inflater.inflate(fragment_page, container, false);


        // Inflate the layout for this fragment

//        // 4 - Get widgets from layout and serialise it
//        LinearLayout rootView = (LinearLayout) result.findViewById(R.id.fragment_page_rootview);
//        TextView textView = (TextView) result.findViewById(R.id.fragment_page_title);
//
//        // 5 - Get data from Bundle (created in method newInstance)
//        int position = getArguments().getInt(KEY_POSITION, -1);
//        int color = getArguments().getInt(KEY_COLOR, -1);
//
//        int realPosition = position+1;
//
//        // 6 - Update widgets with it
//        rootView.setBackgroundColor(color);
//        textView.setText("Page numéro "+realPosition);


//        return result;
//    }


}