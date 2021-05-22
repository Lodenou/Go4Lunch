package com.lodenou.go4lunch.controller.activity.chatactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.lodenou.go4lunch.R;
import com.lodenou.go4lunch.controller.api.MessageHelper;
import com.lodenou.go4lunch.controller.api.UserHelper;
import com.lodenou.go4lunch.model.Message;
import com.lodenou.go4lunch.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.Listener {


        // FOR DESIGN
        // 1 - Getting all views needed
        @BindView(R.id.activity_chat_recycler_view) RecyclerView mRecyclerView;
        @BindView(R.id.activity_mentor_chat_text_view_recycler_view_empty) TextView textViewRecyclerViewEmpty;
        @BindView(R.id.activity_mentor_chat_message_edit_text) TextInputEditText editTextMessage;
        @BindView(R.id.activity_mentor_chat_image_chosen_preview) ImageView imageViewPreview;

        // FOR DATA
        // 2 - Declaring Adapter and data
        private ChatAdapter mChatAdapter;
        @Nullable
        private User modelCurrentUser;
        private String currentChatName;
        private List<Message> mMessages;

        // STATIC DATA FOR CHAT (3)
        private static final String CHAT_NAME_ANDROID = "android";
        private static final String CHAT_NAME_BUG = "bug";
        private static final String CHAT_NAME_FIREBASE = "firebase";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            this.configureRecyclerView();
//            this.configureToolbar();
            this.getCurrentUserFromFirestore();
        }
//
//        @Override
//        public int getFragmentLayout() { return R.layout.activity_chat; }

        // --------------------
        // ACTIONS
        // --------------------

    @OnClick(R.id.activity_mentor_chat_send_button)
    public void onClickSendMessage() {
        // 1 - Check if text field is not empty and current user properly downloaded from Firestore
        if (!TextUtils.isEmpty(editTextMessage.getText()) && modelCurrentUser != null){
            // 2 - Create a new Message to Firestore
            MessageHelper.createMessageForChat(editTextMessage.getText().toString(), this.currentChatName, modelCurrentUser).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            // 3 - Reset text field
            this.editTextMessage.setText("");
        }
    }

//        @OnClick({ R.id.activity_mentor_chat_android_chat_button, R.id.activity_mentor_chat_firebase_chat_button, R.id.activity_mentor_chat_bug_chat_button})
//        public void onClickChatButtons(ImageButton imageButton) {
//            // 8 - Re-Configure the RecyclerView depending chosen chat
//            switch (Integer.valueOf(imageButton.getTag().toString())){
//                case 10:
//                    this.configureRecyclerView(CHAT_NAME_ANDROID);
//                    break;
//                case 20:
//                    this.configureRecyclerView(CHAT_NAME_FIREBASE);
//                    break;
//                case 30:
//                    this.configureRecyclerView(CHAT_NAME_BUG);
//                    break;
//            }
//        }

        @OnClick(R.id.activity_mentor_chat_add_file_button)
        public void onClickAddFile() { }

        // --------------------
        // REST REQUESTS
        // --------------------
        // 4 - Get Current User from Firestore
        private void getCurrentUserFromFirestore(){

            UserHelper.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    modelCurrentUser = documentSnapshot.toObject(User.class);
                }
            });
        }

        // --------------------
        // UI
        // --------------------
        // 5 - Configure RecyclerView with a Query
//        private void configureRecyclerView(String chatName){
//            //Track current chat name
//            this.currentChatName = chatName;
//            //Configure Adapter & RecyclerView
//            this.mChatAdapter = new ChatAdapter(generateOptionsForAdapter(MessageHelper.getAllMessageForChat(this.currentChatName)),
//                    Glide.with(this),
//                    this,
//                    FirebaseAuth.getInstance().getCurrentUser()
//                            .getUid());
//            mChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//                @Override
//                public void onItemRangeInserted(int positionStart, int itemCount) {
//                    recyclerView.smoothScrollToPosition(mChatAdapter.getItemCount()); // Scroll to bottom on new messages
//                }
//            });
//            recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
//            recyclerView.setAdapter(this.mChatAdapter);
//        }

    private void configureRecyclerView() {

        this.mMessages = new ArrayList<>();
        this.mChatAdapter = new ChatAdapter(generateOptionsForAdapter(MessageHelper.getAllMessageForChat(this.currentChatName)),
                    Glide.with(this),
                    this,
                    FirebaseAuth.getInstance().getCurrentUser()
                            .getUid());
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

        // 6 - Create options for RecyclerView from a Query
        private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){
            return new FirestoreRecyclerOptions.Builder<Message>()
                    .setQuery(query, Message.class)
                    .setLifecycleOwner(this)
                    .build();
        }

        // --------------------
        // CALLBACK
        // --------------------

        @Override
        public void onDataChanged() {
            // 7 - Show TextView in case RecyclerView is empty
            textViewRecyclerViewEmpty.setVisibility(this.mChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
}