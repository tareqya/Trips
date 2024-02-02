package com.example.trip.entity;

import java.util.Date;

public class BookedTrip extends Trip{
    private Date tripDate;

    public BookedTrip(){

    }

    public BookedTrip(Trip trip, Date tripDate){
        title = trip.getTitle();
        description = trip.getDescription();
        imagePath = trip.getImagePath();
        imageUrl = trip.getImageUrl();
        unit = trip.getUnit();
        length = trip.getLength();
        tripType = trip.getTripType();
        rate = trip.getRate();
        this.uid = trip.getUid();
        this.tripDate = tripDate;
    }
    public Date getTripDate() {
        return tripDate;
    }

    public BookedTrip setTripDate(Date tripDate) {
        this.tripDate = tripDate;
        return this;
    }
}
