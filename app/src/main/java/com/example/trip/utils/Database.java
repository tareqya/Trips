package com.example.trip.utils;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trip.callback.UserCallBack;
import com.example.trip.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

public class Database {
    public static final String USERS_TABLE = "Users";
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    private FirebaseStorage storage;
    private UserCallBack userCallBack;

    public Database(){
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
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

}
