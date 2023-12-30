package com.example.trip.entity;

import com.google.firebase.database.Exclude;

public class User extends FirebaseId {

    private String fullName;
    private String email;
    private String password;

    public User(){};

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
