package com.lodenou.go4lunch.controller.api;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lodenou.go4lunch.model.User;

import java.util.List;

// THIS CLASS STATICALLY REFERS CRUD REQUESTS
public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String name, String restaurantName, String restaurantPlaceId, String restaurantAddress, String avatarUrl, List<String> favoritesRestaurants) {
        User userToCreate = new User(uid, name, restaurantName, restaurantPlaceId, restaurantAddress, avatarUrl, favoritesRestaurants);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid) {
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


    public static Query getAllUsers() {
        return UserHelper.getUsersCollection();
    }

    // UPDATE USER
    public static void updateUser(final String favorite, final String uid) {
        UserHelper.getUser(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);

                if (user.getFavoritesRestaurants().contains(favorite)) {
                    user.getFavoritesRestaurants().remove(favorite);
                } else {
                    user.getFavoritesRestaurants().add(favorite);
                }

                UserHelper.getUsersCollection().document(uid).update("favoritesRestaurants", user.getFavoritesRestaurants());
            }
        });
    }

    // Update user with restaurant's informations
    public static Task<Void> updateUserWithRestaurantInfo(String uid, String restaurantPlaceId, String restaurantName, String restaurantAddress) {
        return UserHelper.getUsersCollection().document(uid).update("restaurantName", restaurantName, "restaurantPlaceId", restaurantPlaceId, "restaurantAddress", restaurantAddress);
    }
}

