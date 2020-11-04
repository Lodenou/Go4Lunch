package com.lodenou.go4lunch.controller.fragments.workmates;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class WorkMatesFragment extends Fragment {

    MyItemRecyclerViewAdapter mAdapter;
    private List<User> mUsers;
    RecyclerView mRecyclerView;

    public WorkMatesFragment() {
    }


public static WorkMatesFragment newInstance() {
    WorkMatesFragment workMatesFragment = new WorkMatesFragment();
    return workMatesFragment;
}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_mates_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);

        this.mUsers = new ArrayList<>();
        transformQuerytoUser();
        this.mAdapter = new MyItemRecyclerViewAdapter(mUsers);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }


    private void transformQuerytoUser(){
        UserHelper.getAllUsers().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listworkmates = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot item: listworkmates) {
                    User userw =  item.toObject(User.class);
                    mUsers.add(userw);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}