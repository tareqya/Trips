package com.example.trip.utils;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trip.callback.TripCallBack;
import com.example.trip.callback.UserCallBack;
import com.example.trip.entity.BookedTrip;
import com.example.trip.entity.Trip;
import com.example.trip.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    public static final String USERS_TABLE = "Users";
    public static final String TRIPS_TABLE = "Trips";
    public static final String USER_TRIPS_TABLE = "Trips";
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseStorage storage;
    private UserCallBack userCallBack;
    private TripCallBack tripCallBack;

    public Database(){
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public void setTripCallBack(TripCallBack tripCallBack){
        this.tripCallBack = tripCallBack;
    }

    public void createAccount(User user){
        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   String uid = getCurrentUser().getUid();
                   user.setUid(uid);
                   saveUserData(user);
               }
            }
        });
    }

    public void saveUserData(User user){
        this.mDatabase.collection(USERS_TABLE).document(user.getUid()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(userCallBack != null){
                            userCallBack.onCreateAccountComplete(task);
                        }
                    }
                });
    }

    public void fetchUserData(String uid){
        this.mDatabase.collection(USERS_TABLE).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                User user = value.toObject(User.class);
                if(user.getImagePath() != null){
                    String imageUrl = downloadImageUrl(user.getImagePath());
                    user.setImageUrl(imageUrl);
                }
                userCallBack.onFetchUserComplete(user);
            }
        });
    }

    public FirebaseUser getCurrentUser(){
        return this.mAuth.getCurrentUser();
    }

    public void logout(){
        this.mAuth.signOut();
    }

    public void loginUser(String email, String password) {
        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(userCallBack != null){
                    userCallBack.onLoginComplete(task);
                }
            }
        });
    }

    public String downloadImageUrl(String imagePath){
        Task<Uri> downloadImageTask = storage.getReference().child(imagePath).getDownloadUrl();
        while (!downloadImageTask.isComplete() && !downloadImageTask.isCanceled());
        return downloadImageTask.getResult().toString();
    }


    public void fetchTrips(){
        this.mDatabase.collection(TRIPS_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Trip> trips = new ArrayList<>();
                for(DocumentSnapshot snapshot: value.getDocuments()){
                    Trip trip = snapshot.toObject(Trip.class);
                    trip.setUid(snapshot.getId());
                    Log.d("TAREQ", snapshot.getId());
                    if(trip.getImagePath() != null){
                        String imageUrl = downloadImageUrl(trip.getImagePath());
                        trip.setImageUrl(imageUrl);
                    }
                    trips.add(trip);
                }

                tripCallBack.onTripsFetchComplete(trips);
            }
        });
    }

    public void bookTrip(String uid, BookedTrip trip){

        this.mDatabase.collection(USERS_TABLE)
                .document(uid).collection(USER_TRIPS_TABLE).document(trip.getUid())
                .set(trip)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        tripCallBack.onBookTripComplete(task);
                    }
                });
    }

    public void fetchTripByDate(String uid, Date date){
        this.mDatabase.collection(USERS_TABLE).document(uid)
                .collection(USER_TRIPS_TABLE).whereEqualTo("tripDate", date)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                            for(DocumentSnapshot snapshot: value.getDocuments()){

                             }
                    }
                });
    }

}
