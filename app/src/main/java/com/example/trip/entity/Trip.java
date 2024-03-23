package com.example.trip.entity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Trip extends FirebaseId implements Serializable {
    protected String title;
    protected String description;
    protected String imagePath;
    protected String imageUrl;
    protected String unit ;
    protected int length;
    protected String tripType;
    protected float rate;

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
    @Exclude
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

    @Override
    public String toString(){
        return this.length + " " + this.unit;
    }
}
