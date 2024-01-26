package com.example.trip.callback;

import com.example.trip.entity.Trip;

import java.util.ArrayList;

public interface TripCallBack {
    void onTripsFetchComplete(ArrayList<Trip> trips);
}
