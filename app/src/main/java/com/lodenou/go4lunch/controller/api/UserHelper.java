package com.lodenou.go4lunch.controller.api;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lodenou.go4lunch.model.User;

import java.util.List;
// THIS CLASS STATICALLY REFERS CRUD REQUESTS
public class UserHelper {

        private static final String COLLECTION_NAME = "users";

        // --- COLLECTION REFERENCE ---

        public static CollectionReference getUsersCollection(){
            return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
        }

        // --- CREATE ---

        public static Task<Void> createUser(String uid, String username, String urlPicture, List<String> favoritesRestaurants ) {
            User userToCreate = new User(uid, username, urlPicture,favoritesRestaurants );
            return UserHelper.getUsersCollection().document(uid).set(userToCreate);
        }

        // --- GET ---

        public static Task<DocumentSnapshot> getUser(String uid){
            return UserHelper.getUsersCollection().document(uid).get();
        }

        // --- UPDATE ---

        public static Task<Void> updateUsername(String username, String uid) {
            return UserHelper.getUsersCollection().document(uid).update("username", username);
        }
        // --- DELETE ---

        public static Task<Void> deleteUser(String uid) {
            return UserHelper.getUsersCollection().document(uid).delete();
        }

    }

