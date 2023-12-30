package com.example.trip.entity;

import com.google.firebase.database.Exclude;

public class FirebaseId {
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
