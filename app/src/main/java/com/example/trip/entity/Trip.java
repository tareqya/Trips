package com.example.trip.entity;

import com.google.firebase.firestore.Exclude;

public class Trip extends FirebaseId {
    private String title;
    private String description;
    private String imagePath;
    private String imageUrl;
    private String unit ;
    private int length;
    private String tripType;
    private float rate;

    public Trip() {
    }

    public String getTitle() {
        return title;
    }

    public Trip setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Trip setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Trip setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Trip setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Trip setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public int getLength() {
        return length;
    }

    public Trip setLength(int length) {
        this.length = length;
        return this;
    }

    public String getTripType() {
        return tripType;
    }

    public Trip setTripType(String tripType) {
        this.tripType = tripType;
        return this;
    }

    public float getRate() {
        return rate;
    }

    public Trip setRate(float rate){
        this.rate = rate;
        return this;
    }
}
