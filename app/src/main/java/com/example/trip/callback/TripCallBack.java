package com.example.trip.callback;

import com.example.trip.entity.Trip;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public interface TripCallBack {
    void onTripsFetchComplete(ArrayList<Trip> trips);
    void onBookTripComplete(Task<Void> task);
}
