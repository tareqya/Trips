package com.example.trip.entity;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class FirebaseId implements Serializable {
    protected String uid;

    public FirebaseId() {}
    public FirebaseId setUid(String uid) {
        this.uid = uid;
        return this;
    }

    @Exclude
    public String getUid() {
        return uid;
    }
}
