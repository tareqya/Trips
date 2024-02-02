package com.example.trip.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.trip.R;
import com.example.trip.adapter.TripAdapter;
import com.example.trip.callback.TripCallBack;
import com.example.trip.callback.TripListener;
import com.example.trip.entity.Trip;
import com.example.trip.utils.Database;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private Spinner fHome_SP_tripType;
    private RecyclerView fHome_RV_trips;
    private Database database;
    private Context context;
    public static final String[] TRIP_TYPES = {"All", "Neutral", "Museum"};

    public ArrayList<Trip> allTrips = new ArrayList<>();
    public HomeFragment(Context context) {
        this.context = context;
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fHome_RV_trips = view.findViewById(R.id.fHome_RV_trips);
        fHome_SP_tripType = view.findViewById(R.id.fHome_SP_tripType);
    }

    private void initVars() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, TRIP_TYPES);
        fHome_SP_tripType.setAdapter(adapter);
        database.setTripCallBack(new TripCallBack() {
            @Override
            public void onTripsFetchComplete(ArrayList<Trip> trips) {
                allTrips = trips;
                TripAdapter tripAdapter = new TripAdapter(context, trips);
                fHome_RV_trips.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                fHome_RV_trips.setHasFixedSize(true);
                fHome_RV_trips.setItemAnimator(new DefaultItemAnimator());
                fHome_RV_trips.setAdapter(tripAdapter);
            }

            @Override
            public void onBookTripComplete(Task<Void> task) {

            }
        });
        database.fetchTrips();
        fHome_SP_tripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedType = (String) fHome_SP_tripType.getItemAtPosition(i);
                filterTripsByType(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void filterTripsByType(String selectedType) {
        ArrayList<Trip> trips = new ArrayList<>();
        if(!selectedType.equals("All")){
            for(Trip trip: allTrips){
                if(trip.getTripType().equals(selectedType)){
                    trips.add(trip);
                }
            }
        }else{
            trips = allTrips;
        }

        TripAdapter tripAdapter = new TripAdapter(context, trips);
        tripAdapter.setTripListener(new TripListener() {
            @Override
            public void onClick(Trip trip) {
                Intent intent = new Intent(context, TripActivity.class);
                intent.putExtra("SELECTED_TRIP", trip);
                startActivity(intent);
            }
        });
        fHome_RV_trips.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fHome_RV_trips.setHasFixedSize(true);
        fHome_RV_trips.setItemAnimator(new DefaultItemAnimator());
        fHome_RV_trips.setAdapter(tripAdapter);
    }
}