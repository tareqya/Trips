package com.example.trip.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trip.R;
import com.example.trip.utils.Database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ActivityFragment extends Fragment {

    private Database database;
    public ActivityFragment() {
        // Required empty public constructor
        database = new Database();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date tripDate = sdf.parse("02/02/2024");
            String uid = database.getCurrentUser().getUid();
            database.fetchTripByDate(uid, tripDate);
        }catch (Exception e){

        }

        return inflater.inflate(R.layout.fragment_activity, container, false);
    }
}