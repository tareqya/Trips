package com.example.trip.utils;

import androidx.annotation.NonNull;

import com.example.trip.callback.UserCallBack;
import com.example.trip.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    public static final String USERS_TABLE = "Users";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private UserCallBack userCallBack;

    public Database(){
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
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
        this.mDatabase.getReference().child(USERS_TABLE).child(user.getUid()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(userCallBack != null){
                            userCallBack.onCreateAccountComplete(task);
                        }
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
}
