package com.example.trip.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trip.R;
import com.example.trip.adapter.TripAdapter;
import com.example.trip.callback.ActivityCallBack;
import com.example.trip.callback.TripListener;
import com.example.trip.entity.BookedTrip;
import com.example.trip.entity.Question;
import com.example.trip.entity.Trip;
import com.example.trip.utils.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ActivityFragment extends Fragment {

    private Database database;
    private Context context;
    private RecyclerView fActivity_RV_trips;

    public ActivityFragment(Context context) {
        // Required empty public constructor
        database = new Database();
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        database.setActivityCallBack(new ActivityCallBack() {
            @Override
            public void onActivitiesFetchComplete(ArrayList<BookedTrip> trips) {
                TripAdapter<BookedTrip> tripAdapter = new TripAdapter<>(context, trips);
                tripAdapter.setTripListener(new TripListener() {
                    @Override
                    public void onClick(Trip trip) {
                        Intent intent = new Intent(context, QuizActivity.class);
                        intent.putExtra("SELECTED_TRIP", trip);
                        startActivity(intent);
                    }
                });
                fActivity_RV_trips.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                fActivity_RV_trips.setHasFixedSize(true);
                fActivity_RV_trips.setItemAnimator(new DefaultItemAnimator());
                fActivity_RV_trips.setAdapter(tripAdapter);
            }

            @Override
            public void onActivityQuestionsFetchComplete(ArrayList<Question> questions) {

            }
        });

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Note: Month is zero-based
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String date = day + "/" + month + "/" + year;
            Date tripDate = sdf.parse(date);
            String uid = database.getCurrentUser().getUid();
            database.fetchTripByDate(uid, tripDate);
        }catch (Exception e){
            Log.d("ERROR", e.getMessage().toString());
        }
    }

    private void findViews(View view) {
        fActivity_RV_trips = view.findViewById(R.id.fActivity_RV_trips);
    }
}